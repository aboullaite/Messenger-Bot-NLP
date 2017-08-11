package me.aboullaite.nlpmessengerbot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class MessageReceived {

    public String object;
    public List<Entry> entry;


    public static class Entry {
        public long id;
        public long time;
        public List<Messaging> messaging;
    }

    public static class Messaging {
        public Sender sender;
        public Recipient recipient;
        public long timestamp;
        public Message message;
        public Delivery delivery;

    }

    public static class Sender {
        public long id;
    }

    public static class Recipient {
        public long id;
    }

    public static class Message {
        public String mid;
        public long seq;
        public String text;
        public NLP nlp;
    }

    public static class Delivery {
        public List<String> mids;
        public long watermark;
        public long seq;
    }

    public static class NLP{
        @JsonProperty("entities")
        public Map<String,List<Map<String,Object>>> entities;
    }


}
