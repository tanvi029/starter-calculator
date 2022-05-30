package com.example.tanvi.starter_calc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final String ADD_ADDRESS = "com.calculator.add";
  public static final String SUBTRACT_ADDRESS = "com.calculator.subtract";
  public static final String MULTIPLY_ADDRESS = "com.calculator.multiply";
  public static final String DIVIDE_ADDRESS = "com.calculator.divide";
  public static final int PORT = 8888;
  public static final String POWER_ADDRESS = "com.calculator.sqrRoot";
  public final Router router = Router.router(vertx);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.exceptionHandler(error->
    {
      LOG.error("Unhandled : ", error);
    });
    vertx.deployVerticle(new MainVerticle(), ar -> {
      if(ar.failed())
      {
        LOG.error("Failed to deploy : {} ", ar.cause());
        return;
      }
      LOG.info("Deployed {} ", MainVerticle.class);
    });
    vertx.deployVerticle(new OutputVerticle(),ar -> {
      if(ar.failed())
      {
        LOG.error("Failed to deploy : {} ", ar.cause());
        return;
      }
      LOG.info("Deployed {} ", OutputVerticle.class);
    });
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {


    router.route().failureHandler(errorContext->{
      if(errorContext.response().ended()){
        return;
      }
      LOG.error("Route Error:",errorContext.failure());
      errorContext.response()
              .setStatusCode(500)
              .end(new JsonObject().put("message","Something went wrong :").toBuffer());
    });

    router.get("/add/:num1/:num2").handler(this :: add);
    router.get("/sub/:num1/:num2").handler(this :: sub);
    router.get("/mul/:num1/:num2").handler(this :: mul);
    router.get("/div/:num1/:num2").handler(this :: div);
    router.get("/pow/:num1/:num2").handler(this::power);

    vertx.createHttpServer().requestHandler(router)
            .exceptionHandler(error -> {
              LOG.error("HTTP Server Error : ", error);
            })
            .listen(PORT, http -> {
              if (http.succeeded()) {
                startPromise.complete();
                LOG.info("HTTP server started on port 8888");
              } else {
                startPromise.fail(http.cause());
              }
            });
  }

  private void power(RoutingContext routingContext) {
    String num1 = routingContext.pathParam("num1");
    String num2=routingContext.pathParam("num2");
    String message = num1+" "+num2;
    vertx.eventBus().request(POWER_ADDRESS,message, reply->{
      routingContext.request().response().end((String) reply.result().body());
    });
  }


  private void div(RoutingContext routingContext) {
    String num1 = routingContext.pathParam("num1");
    String num2 = routingContext.pathParam("num2");
    String message = num1 + " " + num2;
    vertx.eventBus().request(DIVIDE_ADDRESS, message, reply -> {
      routingContext.request().response().end((String) reply.result().body());
    });

  }

  private void mul(RoutingContext routingContext) {

    String num1 = routingContext.pathParam("num1");
    String num2 = routingContext.pathParam("num2");
    String message = num1 + " " + num2;
    vertx.eventBus().request(MULTIPLY_ADDRESS, message, reply -> {
      routingContext.request().response().end((String) reply.result().body());
    });
  }

  private void sub(RoutingContext routingContext) {

    String num1 = routingContext.pathParam("num1");
    String num2 = routingContext.pathParam("num2");
    String message = num1 + " " + num2;
    vertx.eventBus().request(SUBTRACT_ADDRESS, message, reply -> {
      routingContext.request().response().end((String) reply.result().body());
    });

  }

  private void add(RoutingContext routingContext) {

    String num1 = routingContext.pathParam("num1");
    String num2 = routingContext.pathParam("num2");

    String message = num1 + " " + num2;
    vertx.eventBus().request(ADD_ADDRESS,message, reply -> {
      routingContext.request().response().end((String)reply.result().body());
    });

  }
}