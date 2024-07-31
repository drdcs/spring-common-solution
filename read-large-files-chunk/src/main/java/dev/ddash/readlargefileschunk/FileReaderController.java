package dev.ddash.readlargefileschunk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileReaderController {

    @Autowired
    private FileReadingService fileReadingService;

    @PostMapping("/read-file")
    public String readFile(@RequestParam("file") MultipartFile file) {
        try{
            fileReadingService.readFileInChunks(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return "error reading file";
        }
        return "File read completed.";
    }
}
