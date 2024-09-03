package com.fh.api.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Service
public class PostalQueueService {

    private static final String QUEUE_PACKAGES = "QUEUE_PACKAGES";
    private static final String QUEUE_LETTERS = "QUEUE_LETTERS";
    private static final String RABBITMQ_HOST = "localhost";
    private static final int RABBITMQ_PORT = 30003;

    private final ConnectionFactory connectionFactory;

    public PostalQueueService() {
        this.connectionFactory = createConnectionFactory();
    }

    private ConnectionFactory createConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBITMQ_HOST);
        factory.setPort(RABBITMQ_PORT);
        return factory;
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

            declareQueue(channel, queueName);
            publishMessage(channel, messageId, queueName);
            logMessageSent(messageId, queueName);

        } catch (IOException | TimeoutException ex) {
            handleException(ex);
        }
    }

    private void declareQueue(Channel channel, String queueName) throws IOException {
        channel.queueDeclare(queueName, false, false, false, null);
    }

    private void publishMessage(Channel channel, Long messageId, String queueName) throws IOException {
        String message = messageId.toString();
        channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
    }

    private void logMessageSent(Long messageId, String queueName) {
        System.out.printf("Message with ID %s sent to queue: %s%n", messageId, queueName);
    }

    private void handleException(Exception ex) {
        throw new RuntimeException("Failed to send message to queue", ex);
    }
}
