package dev.ddash.jsonparser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParsedDataDto {
    private Optional<String> name;
    private Optional<String> city;
}
