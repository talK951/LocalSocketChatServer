package ServerStates;

import DB.ChatManagerSingleton;
import Helpers.ChatUpdateListener;
import Helpers.OnlineUsersStatusChecker;

import java.io.IOException;
import java.net.Socket;

public class ServerUpdateState  extends IServerStateProtocol{

    public ServerUpdateState(){
        super();
    }
    @Override
    public IServerStateProtocol Execute(Socket socket) throws IOException {
        OnlineUsersStatusChecker onlineChecker = new OnlineUsersStatusChecker(socket);
        onlineChecker.start();

        ChatUpdateListener updateListener = new ChatUpdateListener(socket);
        updateListener.start();
        while(true){

        }

    }
}
