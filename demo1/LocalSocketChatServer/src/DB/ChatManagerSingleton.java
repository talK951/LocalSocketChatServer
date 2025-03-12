package DB;

import java.io.IOException;
import java.util.ArrayList;

public class ChatManagerSingleton {
    private ArrayList<Chat> chats;
    private static ChatManagerSingleton instance;

    private ChatManagerSingleton() {
        chats = new ArrayList<>();
    }

    public static synchronized ChatManagerSingleton getInstance() throws IOException {
        if (instance == null) {
            instance = new ChatManagerSingleton();
        }
        return instance;
    }

    public void CreateChats(String hostName) {
        ArrayList<Chat> newChats = new ArrayList<>();
        for (Chat c: chats) {
            if (c.users.size() == 1){
                if (!c.users.getFirst().hostName.equals(hostName)) {
                    ArrayList<User> users = new ArrayList<>();
                    users.add(new User(hostName));
                    users.add(c.users.getFirst());
                    Chat newChat = new Chat(users, hostName + " " + c.users.getFirst().hostName);
                    newChats.add(newChat);
                }
            }
        }

        chats.addAll(newChats);

        // ME CHAT CREATION
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(hostName));
        Chat newChat = new Chat(users, hostName);
        chats.add(newChat);

        System.out.println(chats);
    }

    public void AddMsgToChat(String channelName, String hostName, String msg) {
        for (Chat c: chats) {
            if (c.name.equals(channelName)) {
                c.AddMessageToHistory(hostName, msg);
            }
        }
    }

    public String GetChatConvo(String chatName) {
        for (Chat c: chats) {
            System.out.println(chats);
            if (c.name.equals(chatName)) {
                return "m/" + c.toString();
            }
        }
        return "m";
    }
}
