package dev.ddash.jsonparser;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ObjectMapperParsing {
    private final ObjectMapper objectMapper;

    public ObjectMapperParsing(ObjectMapper objectMapper) {
        this.objectMapper = new ObjectMapper();
    }

    public ParsedDataDto parseJsonString(String jsonString) {
        ParsedDataDto person = null;
        try {
            person = objectMapper.readValue(jsonString, ParsedDataDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}
