package com.mcsunnyside.redisbridge.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Message {
    private String namespace;
    private String channel;
    private String data;
}
