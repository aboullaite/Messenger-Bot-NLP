package me.aboullaite.nlpmessengerbot.controller;

import me.aboullaite.nlpmessengerbot.domain.MessageReceived;
import me.aboullaite.nlpmessengerbot.domain.MessageResponse;
import me.aboullaite.nlpmessengerbot.service.MessageHandler;
import me.aboullaite.nlpmessengerbot.utils.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/callback")
public class BotController {

    private final String validationToken;
    private final SendMsg send;

    @Autowired
    MessageHandler messageHandler;

    @Autowired
    public BotController(String validationToken, SendMsg send) {
        this.validationToken = validationToken;
        this.send = send;
    }

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public String health () {
        return "pong";
    }

    @RequestMapping(value = "webhook", method = RequestMethod.GET)
    public String Checkwebhook (@RequestParam("hub.verify_token") final String verify_token,
                               @RequestParam("hub.challenge") final String challenge) {
        if (this.validationToken.equals(verify_token) && challenge != null) {
            return challenge;
        }

        return "Error, wrong validation token";
    }

    @RequestMapping(value = "webhook", method = RequestMethod.POST)
    public void HandleWebhook (@RequestBody final MessageReceived messages , HttpServletRequest request) throws IOException{
        Optional<List<MessageReceived.Messaging>> messagingList = messages.entry.stream().map(e -> e.messaging).filter(message -> message.stream().anyMatch(m -> m.message != null && m.message.text !=null)).findFirst();
        if(messagingList.isPresent())
        messagingList.get().forEach(m -> {
            final MessageResponse response = new MessageResponse();
            response.recipient = new MessageResponse.Recipient();
            response.recipient.id = m.sender.id;
            response.message = new MessageResponse.MessageData();
            response.message.text = messageHandler.handleMessage(m.message.nlp.entities);
            this.send.sendMessage(response);
        });

    }
}
