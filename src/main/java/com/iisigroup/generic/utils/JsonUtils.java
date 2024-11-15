package com.iisigroup.generic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.util.List;
import java.util.Map;

public class JsonUtils {
    @Getter
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    public static String bean2Json(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static String bean2Json(Object obj, FilterProvider filters) throws JsonProcessingException {
        return mapper.writer(filters).writeValueAsString(obj);
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) throws JsonProcessingException {
        return mapper.readValue(jsonStr, objClass);
    }

    public static <T> T json2Bean(String jsonStr, TypeReference<T> typeReference) throws JsonProcessingException {
        return mapper.readValue(jsonStr, typeReference);
    }

    public static JsonNode json2jsonNode(String jsonStr) throws JsonProcessingException {
        return mapper.readTree(jsonStr);
    }

    public static String map2Json(Map<String, Object> map) throws JsonProcessingException {
        return mapper.writeValueAsString(map);
    }

    public static Map<String, Object> json2Map(String jsonStr) throws JsonProcessingException {
        return mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * json : "["uuid-1","uuid-2"]" to String List
     */
    public static List<String> json2List(String jsonStr) throws JsonProcessingException {
        return mapper.readValue(jsonStr, new TypeReference<List<String>>() {});
    }

    /**
     * json : String List  to "["uuid-1","uuid-2"]"
     */
    public static String list2Json(List<String> list) throws JsonProcessingException {
        return mapper.writeValueAsString(list);
    }

}
