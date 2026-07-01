package com.micronet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class messageWindow {
    static class Message {

        String content;
        int timestamp;

        Message(String content, int timestamp) {
            this.content = content;
            this.timestamp = timestamp;
        }

        public String toString() {
            return content + "(" + timestamp + ")";
        }
    }
    public static void main(String[] args) {

        List<Message> messages = Arrays.asList(
                new Message("A",100),
                new Message("B",102),
                new Message("A",105),
                new Message("C",108),
                new Message("B",110)
        );

        List<Message> result =
                filterMessages(messages,10,110);

        System.out.println(result);


    }
    public static List<Message> filterMessages(
            List<Message> messages,
            int windowSize,
            int latestTimestamp) {

            int start = latestTimestamp - windowSize +1;

        HashMap<String, Message> map = new HashMap<>();

        for (Message m : messages) {
            if (m.timestamp < start || m.timestamp > latestTimestamp) {
                continue;
            }
            if (!map.containsKey(m.content)) {
                map.put(m.content, m);
            }

        else{
                Message old = map.get(m.content);
                if(m.timestamp > old.timestamp){
                    map.put(m.content, m);
                }
            }
        }

        return new ArrayList<>(map.values());
    }

}
