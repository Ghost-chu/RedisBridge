package com.mcsunnyside.redisbridge.common.bus;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class BusManager {
    private static final Map<Listener, String> listenerAndNameSpace = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger("RedisBridge");

    public static synchronized void registerListener(@NotNull String namespace, @NotNull Listener listener) {
        listenerAndNameSpace.put(listener, namespace);
    }

    public static void call(@NotNull String namespace, @NotNull String channel, @NotNull String data) {
        listenerAndNameSpace.forEach((listener, listenerNameSpace) -> {
            if (!listenerNameSpace.equals(namespace)) {
                return;
            }
            for (Method method : listener.getClass().getDeclaredMethods()) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                if (!subscribe.channel.equals(channel)) {
                    continue;
                }
                //Check args
                if (method.getParameterCount() != 1) {
                    continue;
                }
                if (!method.getParameterTypes()[0].equals(String.class)) {
                    continue;
                }
                try {
                    method.invoke(listener, data);
                } catch (Throwable throwable) {
                    logger.warning("Listener " + listener.getClass().getCanonicalName() + " generated a exception: ");
                    throwable.printStackTrace();
                }
            }

        });
    }

    @AllArgsConstructor
    @Data
    public static class SubChannels {
        private List<String> channels;
    }
}
