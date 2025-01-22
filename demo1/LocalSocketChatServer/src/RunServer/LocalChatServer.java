package RunServer;

import java.net.*;
import java.io.*;

public class LocalChatServer extends Thread {
    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(8080);

        while (true){
            try{

                Socket clientSocket = serverSocket.accept();
                ChatThread thread  = new ChatThread(clientSocket);
                thread.start();

            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                        + 8080+ " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }


    }
}