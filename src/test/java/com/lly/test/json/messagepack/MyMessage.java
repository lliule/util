package com.lly.test.json.messagepack;

import lombok.Getter;
import lombok.Setter;
import org.msgpack.annotation.Message;
import org.msgpack.annotation.Optional;

@Getter
@Setter
@Message
public class MyMessage {
    private int id;
    private String content;
    @Optional
    private String comment;

    public MyMessage() {
    }

    public MyMessage(int id, String content, String comment) {
        this.id = id;
        this.content = content;
        this.comment = comment;
    }
}
