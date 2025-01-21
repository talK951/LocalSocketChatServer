package RunServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatThread extends Thread {

    private PrintWriter out;
    private BufferedReader in;
    public ChatThread(PrintWriter out, BufferedReader in)
    {

        this.out = out;
        this.in = in;

    }

    @Override
    public void run() {
        String inputLine, outputLine;

        // Initiate conversation with client
        ChatProtocol cP = new ChatProtocol();
        outputLine = cP.processInput(null);
        out.println(outputLine);

        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            outputLine = cP.processInput(inputLine);
            out.println(outputLine);
            if (outputLine.equals("Bye."))
                break;
        }
    }


}
