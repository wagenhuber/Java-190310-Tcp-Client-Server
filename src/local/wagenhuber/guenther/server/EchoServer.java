package local.wagenhuber.guenther.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void startSrver() {

        try {
            ServerSocket server = new ServerSocket(port);

            InetAddress addr = InetAddress.getLocalHost();

            System.out.println("Echoserver auf " + addr.getHostName() + ", mit Adresse " + addr.getHostAddress() + " und Port " + port + " gestartet!");


            while (true) {
                Socket client = server.accept();
                System.out.println(client.toString());
                new EchoThread(client).start();
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private class EchoThread extends Thread{
        private Socket client;

        public EchoThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            String clientAddr = null;

            clientAddr = client.getInetAddress().getHostAddress();

            int clientPort = client.getPort();

            System.out.println("Verbindung zu " + client.getInetAddress().getHostName() + ", mit Adresse " + clientAddr + " und Port " + port + " aufgebaut!");

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                out.println("Server ist bereit ...");

                String input;

                while ((input = in.readLine()) != null) {
                    out.println(input);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Verbindung abgebaut!");
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).startSrver();
    }

}
