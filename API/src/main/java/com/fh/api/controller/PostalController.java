package com.fh.api.controller;

import com.fh.api.services.QueryHandler;
import com.fh.api.services.Data;
import com.fh.api.queue.PostalQueueService;
import com.fh.api.data.DeliveryStatus;
import com.fh.api.data.Letter;
import com.fh.api.data.Packet;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostalController {

    // Database connection instance
    private final Data databaseConnection;

    // Service for handling database queries
    private final QueryHandler queryService;

    // Service for sending messages to the postal queue
    private final PostalQueueService queueSender;

    // Constructor initializes services and database connection
    public PostalController() throws SQLException {
        this.databaseConnection = new Data();  // Initialize database connection
        this.queryService = new QueryHandler(databaseConnection);  // Initialize query handler with the database connection
        this.queueSender = new PostalQueueService();  // Initialize queue service
    }

    /**
     * Handles the submission of a letter.
     *
     * @param letter The letter object submitted by the client
     * @return A response message indicating the status of the submission
     */
    @PostMapping("/letters")
    public String handleLetterSubmission(@RequestBody Letter letter) {
        Long letterId = queryService.insertLetter(letter);  // Insert the letter into the database and get its ID

        try {
            queueSender.enqueueLetter(letterId);  // Enqueue the letter ID for processing
            return "Letter processing initiated for Customer: " + letter.getName();  // Return success message
        } catch (Exception ex) {
            return "Failed to process letter for Customer: " + letter.getName() +
                    "\nError details: " + ex.getMessage();  // Return failure message with error details
        }
    }

    /**
     * Handles the submission of a package.
     *
     * @param packet The package object submitted by the client
     * @return A response message indicating the status of the submission
     */
    @PostMapping("/packages")
    public String handlePackageSubmission(@RequestBody Packet packet) {
        Long packageId = queryService.insertPacket(packet);  // Insert the package into the database and get its ID

        try {
            queueSender.enqueuePackage(packageId);  // Enqueue the package ID for processing
            return "Package processing initiated for Customer: " + packet.getName();  // Return success message
        } catch (Exception ex) {
            return "Failed to process package for Customer: " + packet.getName() +
                    "\nError details: " + ex.getMessage();  // Return failure message with error details
        }
    }

    /**
     * Retrieves the status of all deliveries.
     *
     * @return A list of delivery statuses
     */
    @GetMapping("/status")
    public List<DeliveryStatus> retrieveDeliveryStatuses() {
        return queryService.fetchAllDeliveries();  // Fetch and return all delivery statuses from the database
    }
}