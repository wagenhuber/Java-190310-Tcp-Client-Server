package local.wagenhuber.guenther.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args) {

        Socket socket = null;

        try {
            String host = args[0];
            InetAddress serverAdress = InetAddress.getByName("localhost");
            System.out.println(serverAdress);


            int port = Integer.parseInt(args[1]);
            System.out.println("Verbindungsversucht mit: " + host + " und Port: " + port);


            socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            String msg = in.readLine();
            System.out.println(msg);
            String line;
            while (true) {
                line = input.readLine();
                if (line == null || line.equals("q")) {
                    break;
                }
                out.println(line);
                System.out.println(in.readLine());
                in.close();
                out.close();
                input.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }


    }

}
