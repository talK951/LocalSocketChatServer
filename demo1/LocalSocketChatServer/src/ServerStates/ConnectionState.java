package ServerStates;

import RunServer.ChatProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionState extends IServerStateProtocol{

    public ConnectionState() {
        super();
    }

    @Override
    public IServerStateProtocol Execute(Socket socket) throws IOException {



        PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Writing to user
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Reading from user

        out.println("Connection Established");

        return new GetUserInfoState();
    }
}
