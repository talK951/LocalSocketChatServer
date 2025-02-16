package DB;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {
    public ArrayList<User> users;
    public String name;
    private ArrayList<Message> history = new ArrayList<>();

    public Chat(ArrayList<User> users, String name){
        this.users = users;
        this.name = name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void AddMessageToHistory(String hostName, String msg) {
        Message message = new Message(hostName, msg);
        history.add(message);
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        for (Message msg: history) {
            output.append(msg.toString());
        }
        return output.toString();
    }

}
