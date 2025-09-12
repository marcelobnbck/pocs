import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BatchProcessing {

    public static void main(String[] args) {
        String inputFile;
        String outputFile;

        if (args.length >= 2) {
            inputFile = args[0];
            outputFile = args[1];
        } else {
            inputFile = "input_file.csv";
            outputFile = "output_file.csv";
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            List<String> processed = new ArrayList<>();
            processed.add(lines.get(0)); // keep header
            processed.addAll(
                    lines.stream()
                            .skip(1)
                            .map(line -> {
                                String[] parts = line.split(",");
                                return parts[0].toUpperCase() + "," + parts[1].toUpperCase();
                            })
                            .toList()
            );

            Files.write(Paths.get(outputFile), processed);
            System.out.println("Batch processing completed. Output saved to: " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
