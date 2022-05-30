package com.example.tanvi.starter_calc;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testContext.completeNow();
  }
 @Test
  void add(Vertx vertx,VertxTestContext testContext) throws Throwable{
    var client=WebClient.create(vertx,new WebClientOptions().setDefaultPort(MainVerticle.PORT));
    client.get("/add/3/2")
            .send()
            .onComplete(testContext.succeeding(response ->{
              var message="";
              LOG.info("Response: {}",message);
              assertEquals("",message);
              assertEquals(200,response.statusCode());
              testContext.completeNow();

            }));
  }

  @Test
    void mul(Vertx vertx, VertxTestContext testContext) throws Throwable{
     var client= WebClient.create(vertx, new WebClientOptions().setDefaultPort(MainVerticle.PORT));
     client.get("/mul/3/2")
             .send()
             .onComplete(testContext.succeeding(response ->{
                 var json=response.bodyAsJsonArray();
                 LOG.info("Response: {}",json);
                 assertEquals("",json.encode());
                 assertEquals(200,response.statusCode());
                 testContext.completeNow();
             }));
  }

    @Test
    void div(Vertx vertx,VertxTestContext testContext) throws Throwable{
        var client=WebClient.create(vertx,new WebClientOptions().setDefaultPort(MainVerticle.PORT));
        client.get("/div/3/2")
                .send()
                .onComplete(testContext.succeeding(response ->{
                    var message="";
                    LOG.info("Response: {}",message);
                    assertEquals("",message);
                    assertEquals(200,response.statusCode());
                    testContext.completeNow();

                }));
    }
    @Test
    void sub(Vertx vertx,VertxTestContext testContext) throws Throwable{
        var client=WebClient.create(vertx,new WebClientOptions().setDefaultPort(MainVerticle.PORT));
        client.get("/sub/3/2")
                .send()
                .onComplete(testContext.succeeding(response ->{
                    var message="";
                    LOG.info("Response: {}",message);
                    assertEquals("",message);
                    assertEquals(200,response.statusCode());
                    testContext.completeNow();

                }));
    }
    @Test
    void pow(Vertx vertx,VertxTestContext testContext) throws Throwable{
        var client=WebClient.create(vertx,new WebClientOptions().setDefaultPort(MainVerticle.PORT));
        client.get("/pow/3/2")
                .send()
                .onComplete(testContext.succeeding(response ->{
                    var message="";
                    LOG.info("Response: {}",message);
                    assertEquals("",message);
                    assertEquals(200,response.statusCode());
                    testContext.completeNow();

                }));
    }



}
