package com.paytonrules;

import org.junit.Test;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ConnectException;

import static org.junit.Assert.*;

public class SocketWrapperTest {

  @Test
  public void ItCanStartASocketAtAPort() throws Exception {
    try(SocketWrapper socket = new SocketWrapper(5000)) {
      StartSocket(socket);

      // Try to connect
      boolean connected = false;
      int retries = 0;
      while (!connected && retries < 5) {
        InetAddress host = InetAddress.getLocalHost();
        try(Socket client = new Socket(host.getHostName(), 5000)) {
          connected = true;

          String dataWritten = WriteToSocket(client, "Test\n");

          assertEquals("Test", dataWritten);
        } catch(ConnectException e) {
          Thread.sleep(100);
          retries++;
        }
      }

      // Throw exception if we couldn't retry
      if (retries >= 5) {
        throw new Exception();
      }
    }
  }

  private void StartSocket(SocketWrapper socket) {
      new Thread() {
        public void run() {
          socket.start();
        }
      }.start();
  }

  private String WriteToSocket(Socket client, String data) throws Exception {
    PrintWriter out = new PrintWriter(client.getOutputStream());
    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

    out.println(data);
    out.flush();

    return in.readLine();
  }
}
