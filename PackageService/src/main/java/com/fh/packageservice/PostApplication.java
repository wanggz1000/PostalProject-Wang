package com.fh.packageservice;

import com.fh.packageservice.queue.MessageConsumer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;


public class PostApplication {

        public static void main(String[] args) throws IOException, TimeoutException, SQLException {
            MessageConsumer consume = new MessageConsumer();
            consume.startLetterConsumption();
            consume.startPackageConsumption();
        }
    }


