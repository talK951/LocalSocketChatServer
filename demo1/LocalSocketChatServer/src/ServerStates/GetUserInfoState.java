package ServerStates;

import DB.ChatManagerSingleton;

import java.io.*;
import java.net.Socket;

public class GetUserInfoState extends IServerStateProtocol{

    public static int num = 0;

    public GetUserInfoState() {
        super();
    }

    @Override
    public IServerStateProtocol Execute(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Reading from user
        String userInformation;
        while(true)
        {
            userInformation = in.readLine();
            if (userInformation != null) {
                break;
            }
        }

        try(PrintWriter updateOnline =  new PrintWriter(new FileWriter("demo1/LocalSocketChatServer/HelperFiles/online-users.txt",true))){
            String sNum = String.valueOf(num);
            updateOnline.write(userInformation +"\n");
            num++;
        }
        catch (IOException e){
            System.out.println("lol");

        }

        System.out.println("userInformation: " + userInformation);
        ChatManagerSingleton.getInstance().CreateChats(userInformation);
        return new ServerUpdateState();
    }
}
