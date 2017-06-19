package com.idohoo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * 序列化 处理类
 *
 */
public class SerializableUtil {

	public static<T extends Serializable> byte[] serialize(Object value){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    public static <T extends Serializable> T deSerialize(byte[] bytes){
        ByteArrayInputStream bIs = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream oIs = new ObjectInputStream(bIs);
            return (T)oIs.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
