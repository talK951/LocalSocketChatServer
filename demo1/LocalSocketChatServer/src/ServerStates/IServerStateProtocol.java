package ServerStates;
import RunServer.ChatProtocol;


public abstract class IServerStateProtocol {

    protected ChatProtocol chatProtocol;

    public IServerStateProtocol(ChatProtocol chatProtocol)
    {
        this.chatProtocol = chatProtocol;
    }

    public abstract void Execute();

}
