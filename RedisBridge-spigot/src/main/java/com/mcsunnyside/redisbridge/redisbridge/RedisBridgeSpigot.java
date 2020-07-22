package com.mcsunnyside.redisbridge.redisbridge;

import com.mcsunnyside.redisbridge.common.connection.Connection;
import com.mcsunnyside.redisbridge.common.connection.RedisConnection;
import com.mcsunnyside.redisbridge.common.connection.bus.BusManager;
import com.mcsunnyside.redisbridge.common.connection.bus.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RedisBridgeSpigot extends JavaPlugin {
    private Connection connection;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        connection = new RedisConnection();
        boolean status = connection.connect(getConfig().getString("host"), (short) getConfig().getInt("port"), getConfig().getString("username"), getConfig().getString("password"), getConfig().getBoolean("ssl"));
        if (status) {
            getLogger().info("Successfully connect to Redis server!");
        } else {
            getLogger().severe("Failed connect to Redis server, please check the configuration and system firewall.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Push the message to the bus.
     *
     * @param namespace The namespace
     * @param channel   The channel id
     * @param data      The data, if you want transfer Binary, use Base64.
     * @throws IllegalArgumentException Any args is null
     */
    public void push(@NotNull String namespace, @NotNull String channel, @NotNull String data) throws IllegalArgumentException {
        connection.push(namespace, channel, data);
    }

    /**
     * Register the redis listener to bus.
     *
     * @param namespace The namespace
     * @param listener  The listener you want to register
     */
    public void register(@NotNull String namespace, @NotNull Listener listener) {
        BusManager.registerListener(namespace, listener);
    }
}
