package length;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FirstSolution {
    int lengthCounter;
    int readSymbol;
    int maxLength;
    StringBuilder stringLine = new StringBuilder();
    String finalLine;

    void application(String fileName) {
        try (FileReader fileReader = new FileReader(fileName)) {

            while ((readSymbol = fileReader.read()) != -1) {
                lengthCounter++;
                stringLine.append((char) readSymbol);
                if (readSymbol == '\n' || readSymbol == '\r') {
                    if (lengthCounter > maxLength) {
                        maxLength = lengthCounter - 1;
                        finalLine = stringLine.toString();
                    }
                    lengthCounter = 0;
                    stringLine = new StringBuilder();
                }
            }
            if (lengthCounter > maxLength) {
                maxLength = lengthCounter;
                finalLine = stringLine.toString();
            }
            System.out.println("maxLength: " + maxLength + ", " + "line " + finalLine);
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("IO Exception " + e.getLocalizedMessage());
        }
    }
}
