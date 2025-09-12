import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchProcessingTest {

    private Path inputFile;
    private Path outputFile;

    @BeforeEach
    void setUp() throws IOException {
        inputFile = Files.createTempFile("tmp_input", ".csv");
        outputFile = Files.createTempFile("tmp_output", ".csv");

        Files.write(inputFile, List.of(
                "firstName,lastName",
                "John,Doe",
                "Mary,Jane",
                "Marcelo,Beck"
        ));
    }

    @Test
    void testBatchProcessing() throws IOException {
        BatchProcessing.main(new String[]{inputFile.toString(), outputFile.toString()});

        List<String> outputLines = Files.readAllLines(outputFile);

        assertEquals("firstName,lastName", outputLines.get(0));
        assertEquals("JOHN,DOE", outputLines.get(1));
        assertEquals("MARY,JANE", outputLines.get(2));
        assertEquals("MARCELO,BECK", outputLines.get(3));
    }
}
