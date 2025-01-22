package RunServer;

import java.io.IOException;
import java.net.Socket;

public class ChatThread extends Thread {

    private Socket socket;

    public ChatThread(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        ChatProtocol cP = new ChatProtocol();
        try {
            cP.ExecuteProtocol(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
