package com.paytonrules;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.*;

public class SocketWrapperTest {

  private final FakeCommandProcessor processor = new FakeCommandProcessor();

  @Test
  public void ItCanStartASocketAtAPort() throws Exception {
    try(SocketWrapper socket = new SocketWrapper(5000)) {
      String data = "Test";
      StartSocket(socket);

      // Try to connect
      boolean connected = false;
      int retries = 0;
      while (!connected && retries < 5) {
        InetAddress host = InetAddress.getLocalHost();
        try(Socket client = new Socket(host.getHostName(), 5000)) {
          connected = true;

          String dataWritten = WriteToSocket(client, data+"\n");

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

      assertEquals("data:" + data + " msg: " + processor.getMessage(), data, processor.getMessage());
    }
  }

  private void StartSocket(SocketWrapper socket) {
      new Thread() {
        public void run() {
          socket.setProcessor(processor);
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
