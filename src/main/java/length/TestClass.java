package length;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Fork(value = 1, jvmArgs = {"-Xms256m", "-Xmx256m"})
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Timeout(time = 1, timeUnit = TimeUnit.MINUTES)
public class TestClass {

    @Param("Test.txt")
    String fileName;
    FileReader fileReader;
    int lengthCounter;
    int readSymbol;
    int maxLength;
    StringBuilder stringLine = new StringBuilder();

    @Setup
    public void initFileReader() {
        try {
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void firstTest(Blackhole bh) throws IOException {
        while ((readSymbol = fileReader.read()) != -1) {
            lengthCounter++;
            stringLine.append((char) readSymbol);
            if (readSymbol == '\n' || readSymbol == '\r') {
                if (lengthCounter > maxLength) {
                    maxLength = lengthCounter - 1;
                }
                lengthCounter = 0;
                stringLine = new StringBuilder();
            }
        }
        if (lengthCounter > maxLength) {
            maxLength = lengthCounter;
        }
        bh.consume(maxLength);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void secondTest(Blackhole bh) throws IOException {
        maxLength = Files.lines(Paths.get(fileName)).map(String::length).max(Integer::compare).get();
        bh.consume(maxLength);
    }
}
