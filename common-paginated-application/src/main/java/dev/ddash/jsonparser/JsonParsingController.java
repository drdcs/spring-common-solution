package dev.ddash.jsonparser;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonParsingController {
    @Autowired
    private JsonParsingService jsonParsingService;
    @Autowired
    private ObjectMapperParsing parsing;

    @PostMapping("/extract-data")
    public ParsedDataDto extractData(@RequestBody InputDto query) {
        return jsonParsingService.extractInfoFromJson(query.getQuery());
//        return parsing.parseJsonString(query.getQuery());
    }


}
