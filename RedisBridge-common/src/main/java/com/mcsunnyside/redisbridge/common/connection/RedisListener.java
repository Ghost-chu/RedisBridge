package com.mcsunnyside.redisbridge.common.connection;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mcsunnyside.redisbridge.common.connection.bus.BusManager;
import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {
    private static final String CHANNEL_NAME = "redis-bridge";
    private static final Gson gson = new Gson();

    public void onMessage(String channel, String message) {
        if (!CHANNEL_NAME.equalsIgnoreCase(channel)) {
            return; //Ignore that not us messages.
        }
        if (message.charAt(0) != '{') {
            return; //Drop invalid packet
        }

        try {
            Message processedMessage = gson.fromJson(message, Message.class);
            BusManager.call(processedMessage.getNamespace(), processedMessage.getChannel(), processedMessage.getData());
        } catch (JsonSyntaxException ignored) {
        }

    }
}
