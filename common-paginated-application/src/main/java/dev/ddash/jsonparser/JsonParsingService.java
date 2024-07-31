package dev.ddash.jsonparser;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JsonParsingService {

    public ParsedDataDto extractInfoFromJson(String jsonString) {
        ParsedDataDto dto = new ParsedDataDto();
        /* parse logic */
        try {
            dto.setName(Optional.of(JsonPath.read(jsonString, "$.name")));
        } catch (PathNotFoundException e) {
            dto.setName(Optional.empty());
        }
        try {
            dto.setCity(Optional.of(JsonPath.read(jsonString, "$.address.city")));
        } catch (PathNotFoundException e) {
            dto.setCity(Optional.empty());
        }
        return dto;
    }
}
