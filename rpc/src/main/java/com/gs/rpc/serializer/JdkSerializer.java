package com.gs.rpc.serializer;

import com.gs.rpc.exception.SerializerException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class JdkSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {

        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new SerializerException(e);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException ex) {
                log.error("close io error : {}", ex);
            }
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object object = ois.readObject();
            return (T) object;
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializerException(e);
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException ex) {
                log.error("close io error : {}", ex);
            }
        }
    }
}
