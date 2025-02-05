package RunServer;

import java.net.*;
import java.io.*;
import java.io.File;  // Import the File class

public class LocalChatServer extends Thread {
    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(8080);
        String pathDirectory = "demo1/LocalSocketChatServer/HelperFiles/";



        try {
            File users = new File(pathDirectory + "online-users.txt");
            if (users.createNewFile()) {
                System.out.println("File created: " + users.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


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