package RunServer;
import ServerStates.*;

public class ChatProtocol {

    private IServerStateProtocol state;

    public ChatProtocol(IServerStateProtocol state){
        this.state = state;
    }

    public String processInput(String theInput) {
        return "";
    }
}