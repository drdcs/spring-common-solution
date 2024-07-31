package dev.ddash.readlargefileschunk;


import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class FileReadingService {

    public static final int CHUNK_SIZE = 1024;
    public static final int THREAD_POOL_SIZE = 4;

    public void readFileInChunks(InputStream inputStream) {

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {
            char[] buffer = new char[CHUNK_SIZE];
            int bytesRead;

            while((bytesRead = reader.read(buffer, 0, CHUNK_SIZE)) != -1) {
                char[] chunk = new char[bytesRead];
                System.arraycopy(buffer, 0, chunk, 0, bytesRead);
                executorService.execute(() -> {processChunk(chunk);});
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
            try{
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void processChunk(char[] chunk) {
        String lowerCaseString = new String(chunk).toLowerCase();
        System.out.println(lowerCaseString);
    }
}
