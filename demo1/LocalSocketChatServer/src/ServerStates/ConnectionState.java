package ServerStates;

import RunServer.ChatProtocol;

public class ConnectionState extends IServerStateProtocol{

    public ConnectionState(ChatProtocol chatProtocol) {
        super(chatProtocol);
    }

    @Override
    public void Execute() {

    }
}
