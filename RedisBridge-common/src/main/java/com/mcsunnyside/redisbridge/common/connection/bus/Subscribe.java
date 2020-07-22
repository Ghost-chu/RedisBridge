package com.mcsunnyside.redisbridge.common.connection.bus;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {
    /**
     * The channel id you want to register
     */
    @NotNull
    String channel = "default";
}
