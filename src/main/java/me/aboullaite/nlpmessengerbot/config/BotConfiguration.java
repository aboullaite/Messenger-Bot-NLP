package me.aboullaite.nlpmessengerbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.aboullaite.nlpmessengerbot.controller.BotController;
import me.aboullaite.nlpmessengerbot.utils.SendMsg;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class BotConfiguration {

    public static final String PORT = "PORT";
    public static final int DEFAULT_PORT = 8080;
    public static final String VALIDATION_TOKEN_KEY = "VALIDATION_TOKEN";
    public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    public static final String OMW_TOKEN_KEY = "OMW_TOKEN";
    @Bean
    public OkHttpClient client () {
        return new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

    }

    @Bean
    public ObjectMapper objectMapper () {
        return new ObjectMapper();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(5);
    }

    @Bean
    public BotController botController () {
        return new BotController(this.validationToken(), this.sendMsg());
    }

    @Bean
    public SendMsg sendMsg () {
        return new SendMsg(this.client(), this.objectMapper(), this.executorService(), this.accessToken());
    }

    @Bean
    public SystemEnvironmentPropertySource environmentPropertySource () {
        final Map<String, Object> env = new HashMap<>(System.getenv());
        return new SystemEnvironmentPropertySource("Environment", env);
    }

    @Bean
    public int port () {
        if (this.environmentPropertySource().containsProperty(PORT)) {
            final Integer port = Integer.parseInt(this.environmentPropertySource().getProperty(PORT).toString());
            return port;
        }
        return DEFAULT_PORT;
    }

    @Bean
    public String accessToken () {
        if (this.environmentPropertySource().containsProperty(ACCESS_TOKEN_KEY)) {
            return this.environmentPropertySource().getProperty(ACCESS_TOKEN_KEY).toString();
        }
        return "MISSING_ACCESS_TOKEN";
   }

    @Bean
    public String validationToken () {
        if (this.environmentPropertySource().containsProperty(VALIDATION_TOKEN_KEY)) {
            return this.environmentPropertySource().getProperty(VALIDATION_TOKEN_KEY).toString();
        }
        return "MISSING_VALIDATION_TOKEN";
    }

    @Bean
    public String omwtoken () {
        if (this.environmentPropertySource().containsProperty(OMW_TOKEN_KEY)) {
            return this.environmentPropertySource().getProperty(OMW_TOKEN_KEY).toString();
        }
        return "MISSING_OMW_TOKEN";
    }
}
