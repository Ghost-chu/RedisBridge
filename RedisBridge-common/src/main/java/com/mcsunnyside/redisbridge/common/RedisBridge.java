package com.mcsunnyside.redisbridge.common;

import com.mcsunnyside.redisbridge.common.bus.Listener;
import org.jetbrains.annotations.NotNull;

public interface RedisBridge {

    /**
     * Push the message to the bus line.
     *
     * @param namespace The namespace
     * @param channel   The channel id
     * @param data      The data, if you want transfer Binary, use Base64.
     * @throws IllegalArgumentException Any args is null
     */
    void push(@NotNull String namespace, @NotNull String channel, @NotNull String data) throws IllegalArgumentException;

    /**
     * Register the redis listener to bus.
     *
     * @param namespace The namespace
     * @param listener  The listener you want to register
     */
    void register(@NotNull String namespace, @NotNull Listener listener);
}
