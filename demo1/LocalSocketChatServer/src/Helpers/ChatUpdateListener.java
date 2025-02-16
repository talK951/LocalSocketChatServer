package Helpers;

import DB.ChatManagerSingleton;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatUpdateListener extends Thread {
    private Socket socket;
    private BufferedReader in;
    ChatManagerSingleton manager;

    public ChatUpdateListener(Socket socket) throws IOException {
        this.socket = socket;
        this.manager = ChatManagerSingleton.getInstance();
    }

    @Override
    public void run() {
        try {
            System.out.println("Im here nig");
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                String uiInstruction = in.readLine();  // Read the first line
                System.out.println(uiInstruction);
                if (uiInstruction != null && uiInstruction.length() > 1 && uiInstruction.charAt(0) == 's') {
                    System.out.println("uiInstruction=" + uiInstruction);
                    String[] rawData = uiInstruction.split("/");
                    System.out.println("rawData=" + Arrays.toString(rawData));
                    String convo = manager.GetChatConvo(rawData[1]);
                    System.out.println("convo=" + convo);
                    out.println(convo);
                }

                if (uiInstruction != null && uiInstruction.length() > 1 && uiInstruction.charAt(0) == 'm') {
                    System.out.println("uiInstruction=" + uiInstruction);
                    String[] rawData = uiInstruction.split("/");
                    System.out.println("rawData=" + Arrays.toString(rawData));
                    String channelName = rawData[1];
                    String hostname = rawData[2];
                    String msg = rawData[3];
                    manager.AddMsgToChat(channelName, hostname, msg);
                    String response = manager.GetChatConvo(channelName);
                    System.out.println("response=" + response);
                    out.println(response);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
