package me.aboullaite.nlpmessengerbot.domain;

public class MessageResponse {

    public Recipient recipient;
    public MessageData message;

    public static class Recipient {
        public long id;
    }

    public static class MessageData {
        public String text;
    }
}
