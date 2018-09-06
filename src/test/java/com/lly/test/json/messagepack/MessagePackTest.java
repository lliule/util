package com.lly.test.json.messagepack;

import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MessagePack 序列化和反序列化。
 * 比json数据信息更小。
 */
public class MessagePackTest {

    /**
     * list 序列化和反序列化
     */
    @Test
    public void testSerialize(){
        List<String> list = new ArrayList<>();
        list.add("dana");
        list.add("candice");
        list.add("vic");
        MessagePack messagePack = new MessagePack();
        List<String> read;
        try {
            byte[] bytes = messagePack.write(list);
//            read = this.convertValueDirectly(messagePack,bytes);
            read = this.convertValue(messagePack,bytes);
            for (String s : read) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAnnotation(){
        MyMessage message = new MyMessage(1, "DANA", "handsome man");
        MessagePack messagePack = new MessagePack();
        messagePack.register(MyMessage.class);
        try {
            byte[] bytes = messagePack.write(message);
            Value read = messagePack.read(bytes);
            System.out.println(read.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 先获取值，再将值幻化成对应的Object
     * @param messagePack <code>MessagePack</code>
     * @param bytes 二进制数据
     * @return List<String>
     * @throws IOException io
     */
    private List<String> convertValue(MessagePack messagePack, byte[] bytes) throws IOException {
        Value read = messagePack.read(bytes);
        return new Converter(read).read(Templates.tList(Templates.TString));
    }

    /**
     * 直接反序列化获取值
     * @param messagePack <code>MessagePack</code>
     * @param bytes 二进制数据信息
     * @return List<String>
     * @throws IOException io
     */
    private List<String> convertValueDirectly(MessagePack messagePack, byte[] bytes) throws IOException {
        return messagePack.read(bytes, Templates.tList(Templates.TString));
    }

}
