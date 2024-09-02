package com.fh.packageservice.service;

import java.sql.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryHandler {

    private Connection dbConnection;
    private static final Logger logger = LoggerFactory.getLogger(QueryHandler.class);
    private static final String STATUS_APPROVED = "approved";
    private static final String STATUS_DENIED = "denied";

    public QueryHandler() throws SQLException {
        this.dbConnection = DatabaseConnection.connect(); // Use DatabaseConnection here
    }

    public void assessLetter(Long letterId) {
        String sqlQuery = "SELECT country FROM letter WHERE id = ?";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, letterId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                String destinationCountry = resultSet.getString("country");
                String deliveryStatus = List.of("AT", "CH", "DE").contains(destinationCountry) ? STATUS_APPROVED : STATUS_DENIED;
                updateDeliveryStatus("letter", letterId, deliveryStatus);
            }
        } catch (SQLException e) {
            logger.error("Letter verification failed", e);
            throw new IllegalStateException("Letter verification failed");
        }
    }

    public void assessPackage(Long packageId) {
        String sqlQuery = "SELECT weight FROM package WHERE id = ?";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, packageId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                double packageWeight = resultSet.getDouble("weight");
                String deliveryStatus = packageWeight < 25 ? STATUS_APPROVED : STATUS_DENIED;
                updateDeliveryStatus("package", packageId, deliveryStatus);
            }
        } catch (SQLException e) {
            logger.error("Package verification failed", e);
            throw new IllegalStateException("Package verification failed");
        }
    }

    private void updateDeliveryStatus(String tableName, Long id, String status) {
        String sqlQuery = String.format("UPDATE %s SET status = ? WHERE id = ?", tableName);

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, id);
            if (preparedStatement.executeUpdate() != 1) {
                throw new IllegalStateException("Status update failed");
            }
        } catch (SQLException e) {
            logger.error("Failed to update status for package", e);
            throw new IllegalStateException("Failed to update status for package");
        }
    }
}
