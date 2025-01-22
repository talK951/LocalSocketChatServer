package ServerStates;
import RunServer.ChatProtocol;

import java.io.IOException;
import java.net.Socket;


public abstract class IServerStateProtocol {

    public IServerStateProtocol() {
    }

    public abstract IServerStateProtocol Execute (Socket socket) throws IOException;

}
