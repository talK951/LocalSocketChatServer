package Helpers;
import DB.ChatManagerSingleton;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.util.Arrays;

public class OnlineUsersStatusChecker extends Thread{

    Socket socket;
    public OnlineUsersStatusChecker(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader onlineReader = new BufferedReader(new FileReader("demo1/LocalSocketChatServer/HelperFiles/online-users.txt"));
            Path directoryPath = Paths.get("demo1/LocalSocketChatServer/HelperFiles");
            WatchService onlineWatch = FileSystems.getDefault().newWatchService();
            String line;
            String tag = "u";

            directoryPath.register(onlineWatch,StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            StringBuilder listOfPeople = new StringBuilder();

            while((line = onlineReader.readLine()) != null){
                listOfPeople.append("/").append(line);
//                out.println(tag + "/" + line);
            }
            onlineReader.close();
            out.println(tag + listOfPeople);

            SocketContentRouter dropTest = new SocketContentRouter(socket);
            dropTest.start();

            while (true) {
                WatchKey key = onlineWatch.take();
                for (WatchEvent<?> event : key.pollEvents())
                {
                    // Handle the specific event
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE)
                    {
                        System.out.println("File created: " + event.context());
                    }
                    else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE)
                    {
                        System.out.println("File deleted: " + event.context());
                    }
                    else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY)
                    {
                        System.out.println("IM HERE3");
                        StringBuilder listOfPeople2 = new StringBuilder();
                        String line2;
                        BufferedReader oR = new BufferedReader(new FileReader("demo1/LocalSocketChatServer/HelperFiles/online-users.txt"));
                        while((line2 = oR.readLine()) != null){
                            listOfPeople2.append("/").append(line2);

                        }
                        oR.close();
                        out.println(tag + listOfPeople2);

                    }
                }

                // To receive further events, reset the key
                key.reset();
            }


        } catch (IOException | InterruptedException e)
        {

        }
    }
}

class SocketContentRouter extends Thread {
    private BufferedReader in;
    private PrintWriter out;
    private static Socket socket;
    private ChatManagerSingleton manager;


    public SocketContentRouter(Socket socket) throws IOException {
        this.socket = socket;
        manager = ChatManagerSingleton.getInstance();
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String uiInstruction = in.readLine();  // Read the first line
                if (uiInstruction != null && uiInstruction.length() > 1 && uiInstruction.charAt(0) == 'c') {
                    deleteDataFromFile("demo1/LocalSocketChatServer/HelperFiles/online-users.txt",uiInstruction.substring(1));
                }
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



    public static void deleteDataFromFile(String filePath, String dataToDelete) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        try {
            // Read from the file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            int count = 0;
            boolean hasFound = false;
            while ((line = reader.readLine()) != null) {

                // Remove specific data
                if (!line.contains(dataToDelete) || (line.contains(dataToDelete) && hasFound)) {
                    stringBuilder.append(line).append("\n");

                } else if (line.contains(dataToDelete)) {
                    out.println("r" + count);
                    System.out.println(dataToDelete + " Deleted");
                    hasFound = true;
                }
                count++;
            }
            reader.close();

            // Write back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(stringBuilder.toString());
            writer.close();

            System.out.println("Data deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
