package com.fh.api.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class PostalQueueService {

    private static final String QUEUE_PACKAGES = "QUEUE_PACKAGES";
    private static final String QUEUE_LETTERS = "QUEUE_LETTERS";
    private static final String RABBITMQ_HOST = "localhost";
    private static final int RABBITMQ_PORT = 30003;
    private static ConnectionFactory connectionFactory;

    public PostalQueueService() {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOST);
        connectionFactory.setPort(RABBITMQ_PORT);
    }

    public void enqueueLetter(Long letterId) {
        enqueueMessage(letterId, QUEUE_LETTERS);
    }

    public void enqueuePackage(Long packageId) {
        enqueueMessage(packageId, QUEUE_PACKAGES);
    }

    private void enqueueMessage(Long messageId, String queueName) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(queueName, false, false, false, null);
            String message = messageId.toString();
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.printf("Message with ID %s sent to queue: %s%n", messageId, queueName);

        } catch (IOException | TimeoutException ex) {
            throw new RuntimeException("Failed to send message to queue", ex);
        }
    }
}
