package DB;

import java.net.Socket;

public class Message {
    String host;
    String msg;


    public Message(String host, String msg){
        this.host = host;
        this.msg = msg;
    }

    public String toString(){
        return host + ":" +  msg + "/";
    }
}
