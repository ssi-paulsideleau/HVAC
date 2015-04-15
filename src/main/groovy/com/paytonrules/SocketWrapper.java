package com.paytonrules;

import com.surveysampling.apps.hvac.hardware.CommandProcessor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketWrapper implements java.lang.AutoCloseable {
  private int port;
  private ServerSocket serverSocket;

  private CommandProcessor processor;

  public void setProcessor(CommandProcessor processor) {
    this.processor = processor;
  }

  public SocketWrapper(int port) {
    this.port = port;
  }

  public void start() {
    String input=null;
    try {
      serverSocket = new ServerSocket(this.port);

      while (true) {
        try (Socket socket = serverSocket.accept()) {
          System.out.println("socket created " + socket);

          try (PrintWriter out = new PrintWriter(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            // accept input message
            input = in.readLine();
            // echo message to caller
            out.println(input);

            processor.process(input);

            out.flush();
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
   
  }
}

