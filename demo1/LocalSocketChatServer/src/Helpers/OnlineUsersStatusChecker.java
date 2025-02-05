package Helpers;
import java.io.*;
import java.net.Socket;
import java.nio.file.*;

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

            while((line = onlineReader.readLine()) != null){
                out.println( tag + line);
            }
            CheckForUserDrop dropTest = new CheckForUserDrop(socket);
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
                        String line2;
                        while((line2 = onlineReader.readLine()) != null){
                           out.println(tag + line2);
                        }

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

class CheckForUserDrop extends Thread {
    private BufferedReader in;
    private static Socket dropSocket;


    public CheckForUserDrop(Socket dropSocket) throws IOException {
        this.dropSocket = dropSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader onlineFileRead = new BufferedReader(new FileReader("demo1/LocalSocketChatServer/HelperFiles/online-users.txt"));

            this.in = new BufferedReader(new InputStreamReader(dropSocket.getInputStream()));
            while (true) {
                String line1 = in.readLine();  // Read the first line
                if (line1 != null && line1.length() > 1 && line1.charAt(0) == 'c') {

                    deleteDataFromFile("demo1/LocalSocketChatServer/HelperFiles/online-users.txt",line1.substring(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static void deleteDataFromFile(String filePath, String dataToDelete) throws IOException {
        PrintWriter out = new PrintWriter(dropSocket.getOutputStream(), true);

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
