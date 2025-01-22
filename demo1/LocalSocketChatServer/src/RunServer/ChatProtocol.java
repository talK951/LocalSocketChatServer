package RunServer;
import ServerStates.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatProtocol {

    private IServerStateProtocol state;

    public ChatProtocol(){
        state = new ConnectionState();
    }

    public IServerStateProtocol getState() { return state; }

    public void setState(IServerStateProtocol state) {
        if (state instanceof ConnectionState) {
            this.state = new GetUserInfoState();
        } else if (state instanceof GetUserInfoState) {
            this.state = null;
        }
    }

    public String ExecuteProtocol(Socket socket) throws IOException {
        //if (this.state instanceof ConnectionState)
        //{
        while (true) {
            state = state.Execute(socket);
            if (state == null) {break;}
        }
        // state.establishConnection
        // state = new getUserInfo()
        // state.Execute()
        // state = new UpdateUser()
        // state.Execute()
        // state = new GetUserUpdate()
        //state = new UpdateUser()
        // state.Execute()
        // state = new GetUserUpdate()
        return "";
    }
}