package com.fh.api.services;

import com.fh.api.data.DeliveryStatus;
import com.fh.api.data.Letter;
import com.fh.api.data.Packet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryHandler {

    private final Connection dbConnection;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(QueryHandler.class);

    public QueryHandler(Data dbData) throws SQLException {
        this.dbConnection = dbData.Connection();
    }

    public Long insertLetter(Letter letter) {
        return insertRecord(
                "INSERT INTO letter(name, country, status) VALUES(?, ?, ?)",
                letter.getName(), letter.getCountry(), letter.getStatus()
        );
    }

    public Long insertPacket(Packet packet) {
        return insertRecord(
                "INSERT INTO package(name, weight, status) VALUES(?, ?, ?)",
                packet.getName(), packet.getWeight(), packet.getStatus()
        );
    }

    public List<DeliveryStatus> fetchAllDeliveries() {
        return Stream.concat(
                retrieveDeliveriesByType("letter", "Letter").stream(),
                retrieveDeliveriesByType("package", "Package").stream()
        ).collect(Collectors.toList());
    }

    private Long insertRecord(String sqlQuery, Object... params) {
        try (PreparedStatement statement = dbConnection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return executeInsert(statement);
        } catch (SQLException ex) {
            logger.error("Failed to insert record", ex);
            throw new IllegalStateException("Failed to insert record");
        }
    }

    private Long executeInsert(PreparedStatement statement) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 1) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        }
        throw new IllegalStateException("Failed to insert letter/package");
    }

    private List<DeliveryStatus> retrieveDeliveriesByType(String tableName, String itemType) {
        String sqlQuery = "SELECT id, name, status FROM " + tableName;
        List<DeliveryStatus> deliveryList = new ArrayList<>();

        try (PreparedStatement statement = dbConnection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                deliveryList.add(new DeliveryStatus(
                        resultSet.getLong("id"),
                        itemType,
                        resultSet.getString("name"),
                        resultSet.getString("status")
                ));
            }
        } catch (SQLException ex) {
            logger.error("Failed to retrieve " + itemType + " deliveries", ex);
            throw new IllegalStateException("Failed to retrieve " + itemType + " deliveries");
        }
        return deliveryList;
    }
}