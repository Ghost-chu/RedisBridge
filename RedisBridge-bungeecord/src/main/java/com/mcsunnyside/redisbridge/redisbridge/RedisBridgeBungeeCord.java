package com.mcsunnyside.redisbridge.redisbridge;

import com.mcsunnyside.redisbridge.common.connection.Connection;
import com.mcsunnyside.redisbridge.common.connection.RedisConnection;
import com.mcsunnyside.redisbridge.common.connection.bus.BusManager;
import com.mcsunnyside.redisbridge.common.connection.bus.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class RedisBridgeBungeeCord extends Plugin {
    private Connection connection;
    private Configuration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        getLogger().info("Loading up configurations...");
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        connection = new RedisConnection();
        boolean status = connection.connect(config.getString("host"), config.getShort("port"), config.getString("username"), config.getString("password"), config.getBoolean("ssl"));
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
     * Push the message to the bus line.
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
