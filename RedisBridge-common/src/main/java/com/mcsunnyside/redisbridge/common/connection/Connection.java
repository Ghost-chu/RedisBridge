package com.mcsunnyside.redisbridge.common.connection;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Connection {
    boolean connect(@NotNull String host, short port, @Nullable String user, @Nullable String password, boolean ssl);

    void disconnect();

    long push(@NotNull String namespace, @NotNull String channel, @NotNull String data);

}