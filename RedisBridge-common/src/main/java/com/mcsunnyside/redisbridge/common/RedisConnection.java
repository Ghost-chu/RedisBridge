package com.mcsunnyside.redisbridge.common;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Jedis;

public class RedisConnection implements Connection {
    private static final String CHANNEL_NAME = "redis-bridge";
    private static final Gson gson = new Gson();
    private Jedis jedis;

    @Override
    public boolean connect(@NotNull String host, short port, @Nullable String user, @Nullable String password, boolean ssl) throws IllegalArgumentException {
        jedis = new Jedis(host, port);
        String code;
        if (password != null) {
            if (user == null) {
                code = jedis.auth(password);
            } else {
                code = jedis.auth(user, password);
            }
            return code.equalsIgnoreCase("OK");
        }
        jedis.connect();
        jedis.subscribe(new RedisListener(), CHANNEL_NAME);
        return jedis.isConnected();
    }

    @Override
    public void disconnect() {
        jedis.disconnect();
    }

    public boolean isConnected() {
        return jedis.isConnected();
    }

    public long push(@NotNull String namespace, @NotNull String channel, @NotNull String data) {
        Message message = new Message(namespace, channel, data);
        return jedis.publish(CHANNEL_NAME, gson.toJson(message));
    }
}
