package com.fh.packageservice.queue;

import com.fh.packageservice.service.QueryHandler;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class MessageConsumer {

    private static final String QUEUE_PACKAGES = "QUEUE_PACKAGES";
    private static final String QUEUE_LETTERS = "QUEUE_LETTERS";
    private ConnectionFactory connectionFactory;
    private QueryHandler queryHandler;

    public MessageConsumer() throws SQLException {
        initializeConnectionFactory();
        this.queryHandler = new QueryHandler();
    }

    private void initializeConnectionFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(30003);
    }

    public void startLetterConsumption() throws IOException, TimeoutException {
        try (Connection connection = this.connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            declareQueue(channel, QUEUE_LETTERS);

            System.out.println(" [*] Awaiting messages from Letters Queue. Press CTRL+C to exit.");

            channel.basicConsume(QUEUE_LETTERS, true, createDeliverCallbackForLetter(), this::handleCancellation);
        }
    }

    public void startPackageConsumption() throws IOException, TimeoutException {
        try (Connection connection = this.connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            declareQueue(channel, QUEUE_PACKAGES);

            System.out.println(" [*] Awaiting messages from Packages Queue. Press CTRL+C to exit.");

            channel.basicConsume(QUEUE_PACKAGES, true, createDeliverCallbackForPackage(), this::handleCancellation);
        }
    }

    private void declareQueue(Channel channel, String queueName) throws IOException {
        channel.queueDeclare(queueName, false, false, false, null);
    }

    private DeliverCallback createDeliverCallbackForLetter() {
        return (consumerTag, delivery) -> {
            Long letterId = Long.parseLong(new String(delivery.getBody(), StandardCharsets.UTF_8));
            System.out.println("Letter received with ID: " + letterId);
            queryHandler.assessLetter(letterId);
        };
    }

    private DeliverCallback createDeliverCallbackForPackage() {
        return (consumerTag, delivery) -> {
            Long packageId = Long.parseLong(new String(delivery.getBody(), StandardCharsets.UTF_8));
            System.out.println("Package received with ID: " + packageId);
            queryHandler.assessPackage(packageId);
        };
    }

    private void handleCancellation(String consumerTag) {
        System.out.println("Consumption has been cancelled.");
    }
}
