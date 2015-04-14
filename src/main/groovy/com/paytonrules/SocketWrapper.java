package com.paytonrules;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketWrapper implements java.lang.AutoCloseable {
  private int port;
  private ServerSocket serverSocket;
  private Socket socket;

  public SocketWrapper(int port) {
    this.port = port;
  }

  public void start() {
    try {
      serverSocket = new ServerSocket(this.port);
      socket = serverSocket.accept();
      System.out.println("socket created "+socket);

      PrintWriter out = new PrintWriter(socket.getOutputStream());
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String input = in.readLine();
      out.println(input);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      System.out.println("closing SocketWrapper "+socket);
      socket.close();
      serverSocket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

