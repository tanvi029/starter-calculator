package com.example.tanvi.starter_calc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(OutputVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        vertx.eventBus().consumer(MainVerticle.ADD_ADDRESS, message -> {
            add(message);
        });

        vertx.eventBus().consumer(MainVerticle.SUBTRACT_ADDRESS, message -> {
            subtract(message);
        });

        vertx.eventBus().consumer(MainVerticle.MULTIPLY_ADDRESS, message -> {
            multiply(message);
        });

        vertx.eventBus().consumer(MainVerticle.DIVIDE_ADDRESS, message -> {
            divide(message);
        });
        vertx.eventBus().consumer(MainVerticle.POWER_ADDRESS,message -> {
            power(message);
        });
        startPromise.complete();
    }



    private void divide(Message<Object> message) {

        String[] val = message.body().toString().split(" ");


        double num1 = Double.parseDouble(val[0]);
        double num2 = Double.parseDouble(val[1]);
        if (num2 == 0.0) {
            message.reply("Cannot be divided by zero.");
            LOG.error("Division by zero error !");
            return;
        }
        double result = num1 / num2;
        message.reply("Dividing " + String.valueOf(num1) + " by " + String.valueOf(num2) + " Gives result = " + String.valueOf(result));

    }

    private void multiply(Message<Object> message) {

        String[] val = message.body().toString().split(" ");

        double num1 = Double.parseDouble(val[0]);
        double num2 = Double.parseDouble(val[1]);
        double result = num1 * num2;
        message.reply("Multiplying " + String.valueOf(num1) + " and " + String.valueOf(num2) + " Gives result = " + String.valueOf(result));

    }

    private void subtract(Message<Object> message) {


        String[] val = message.body().toString().split(" ");
        double num1 = Double.parseDouble(val[0]);
        double num2 = Double.parseDouble(val[1]);
        double result = num1 - num2;
        message.reply("Subtracting " + String.valueOf(num2) + " from " + String.valueOf(num1) + " Gives result = " + String.valueOf(result));

    }

    private void add(Message<Object> message) {

        String[] val = message.body().toString().split(" ");
        double num1 = Double.parseDouble(val[0]);
        double num2 = Double.parseDouble(val[1]);
        double result = num1 + num2;
        message.reply("Adding " + String.valueOf(num1) + " and " + String.valueOf(num2) + " Gives Result = " + String.valueOf(result));
    }

    private void power(Message<Object> message) {
        String[] val = message.body().toString().split(" ");
        double num1 = Double.parseDouble(val[0]);
        double num2 = Double.parseDouble(val[1]);
        double result= Math.pow(num1,num2);
        message.reply("Power of " + String.valueOf(num1) + " on " + String.valueOf(num2) + " Gives result = " + String.valueOf(result));


    }
}
