package length;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SecondSolution {
    void application(String fileName) {
        try {
            int maxLength = Files.lines(Paths.get(fileName)).map(String::length).max(Integer::compare).get();
            System.out.println(maxLength);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
