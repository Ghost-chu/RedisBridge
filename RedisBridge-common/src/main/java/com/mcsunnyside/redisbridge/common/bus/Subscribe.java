package com.mcsunnyside.redisbridge.common.bus;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Subscribe {
    /**
     * The channel id you want to register
     */
    String channel() default "default";
}
