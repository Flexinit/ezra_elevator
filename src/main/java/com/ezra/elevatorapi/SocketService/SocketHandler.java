package com.ezra.elevatorapi.SocketService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class SocketHandler {


    @Value("${socket.port}") // Read port number from configuration
    private  int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    @PostConstruct
    public void init() {
        CompletableFuture.runAsync(() -> {
            try {
                start();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle exception
            }
        });
    }


    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // Start a separate thread to continuously read from the socket
        log.info("ELEVATOR SOCKET STARTED");
        new Thread(this::handleIncomingMessages).start();
    }

    @PreDestroy
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void handleIncomingMessages() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Process the incoming message
                log.info("ELEVATOR STATUS: " + inputLine);
            }
        } catch (IOException e) {
            System.err.println("Error reading from socket: " + e.getMessage());
        }
    }
}
