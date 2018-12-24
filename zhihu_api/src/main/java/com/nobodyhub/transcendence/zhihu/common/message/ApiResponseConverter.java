package com.nobodyhub.transcendence.zhihu.common.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Optional;

@Slf4j
@Component
public class ApiResponseConverter {
    private final ObjectMapper objectMapper;

    public ApiResponseConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Deserialize the message from byte[] to required class
     *
     * @param message serialized byte array
     * @param clz     class of target object
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Optional<T> convert(byte[] message, Class<T> clz) {
        log.info("Convert message to class[{}].", clz.getName());
        try (ByteArrayInputStream ios = new ByteArrayInputStream(message)) {
            ObjectInput in = new ObjectInputStream(ios);
            byte[] object = (byte[]) in.readObject();
            if (clz == String.class) {
                return (Optional<T>) Optional.of(new String(object));
            } else {
                T obj = objectMapper.readValue(object, clz);
                return Optional.of(obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("Error happens when convert message.", e);
        }
        return Optional.empty();
    }
}
