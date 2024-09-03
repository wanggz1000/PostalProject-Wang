package com.fh.packageservice.service;

import java.sql.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryHandler {

    private final Connection dbConnection;
    private static final Logger logger = LoggerFactory.getLogger(QueryHandler.class);
    private static final String STATUS_APPROVED = "approved";
    private static final String STATUS_DENIED = "denied";

    public QueryHandler() throws SQLException {
        this.dbConnection = DatabaseConnection.connect();
    }

    public void assessLetter(Long letterId) {
        String sqlQuery = "SELECT country FROM letter WHERE id = ?";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, letterId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String destinationCountry = resultSet.getString("country");
                    String deliveryStatus = determineLetterStatus(destinationCountry);
                    updateDeliveryStatus("letter", letterId, deliveryStatus);
                } else {
                    throw new IllegalStateException("Letter not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Letter verification failed", e);
            throw new IllegalStateException("Letter verification failed", e);
        }
    }

    public void assessPackage(Long packageId) {
        String sqlQuery = "SELECT weight FROM package WHERE id = ?";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, packageId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double packageWeight = resultSet.getDouble("weight");
                    String deliveryStatus = determinePackageStatus(packageWeight);
                    updateDeliveryStatus("package", packageId, deliveryStatus);
                } else {
                    throw new IllegalStateException("Package not found");
                }
            }
        } catch (SQLException e) {
            logger.error("Package verification failed", e);
            throw new IllegalStateException("Package verification failed", e);
        }
    }

    private String determineLetterStatus(String country) {
        return List.of("AT", "CH", "DE").contains(country) ? STATUS_APPROVED : STATUS_DENIED;
    }

    private String determinePackageStatus(double weight) {
        return weight < 25 ? STATUS_APPROVED : STATUS_DENIED;
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
            logger.error("Failed to update status for " + tableName, e);
            throw new IllegalStateException("Failed to update status for " + tableName, e);
        }
    }
}
