package ServerStates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GetUserInfoState extends IServerStateProtocol{

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
        System.out.println("userInformation: " + userInformation);
        /*
        if userInformation.instruction == state 3 {
        return state 3
        }
        if userInfromation.instruction == stat 5 {
        return state 5
        }
         */
        return null;
    }
}
