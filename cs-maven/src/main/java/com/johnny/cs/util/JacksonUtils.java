package com.johnny.cs.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

@Log4j2
public final class JacksonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonUtils(){}

    public static ObjectMapper getMapper(){
        return mapper;
    }
}
