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
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(30003);
        this.queryHandler = new QueryHandler();
    }

    public void startLetterConsumption() throws IOException, TimeoutException {
        Connection connection = this.connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_LETTERS, false, false, false, null);

        System.out.println(" [*] Awaiting messages from Letters Queue. Press CTRL+C to exit.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            Long letterId = Long.parseLong(new String(delivery.getBody(), StandardCharsets.UTF_8));
            System.out.println("Letter received with ID: " + letterId);
            queryHandler.assessLetter(letterId);
        };

        channel.basicConsume(QUEUE_LETTERS, true, deliverCallback, consumerTag -> {
            System.out.println("Letter consumption has been cancelled.");
        });
    }

    public void startPackageConsumption() throws IOException, TimeoutException {
        Connection connection = this.connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_PACKAGES, false, false, false, null);

        System.out.println(" [*] Awaiting messages from Packages Queue. Press CTRL+C to exit.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            Long packageId = Long.parseLong(new String(delivery.getBody(), StandardCharsets.UTF_8));
            System.out.println("Package received with ID: " + packageId);
            queryHandler.assessPackage(packageId);
        };

        channel.basicConsume(QUEUE_PACKAGES, true, deliverCallback, consumerTag -> {
            System.out.println("Package consumption has been cancelled.");
        });
    }
}
