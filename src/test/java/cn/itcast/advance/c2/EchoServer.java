package cn.itcast.advance.c2;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This program implements a simple server that listens to port 8189 and echoes back all client
 * input.
 *
 * @author Cay Horstmann
 * @version 1.21 2012-05-19
 */
@Slf4j
public class EchoServer {
    public static void main(String[] args) throws IOException {
        // establish server socket
        try (ServerSocket s = new ServerSocket(8189)) {
            // wait for client connection
            try (Socket incoming = s.accept()) {
                log.info("1");
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                try (Scanner in = new Scanner(inStream, "UTF-8")) {
                    log.info("2");
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "UTF-8"), true /* autoFlush */);

                    out.println("Hello! Enter BYE to exit.");
                    log.info("3");

                    // echo client input
                    boolean done = false;
                    while (!done && in.hasNextLine()) {
                        log.info("4:{}", in.hasNextLine());

                        String line = in.nextLine();
                        out.println("Echo: " + line);
                        System.out.println(line);
                        if (line.trim().equals("BYE")) {
                            done = true;
                        }
                    }
                }
            }
        }
    }
}