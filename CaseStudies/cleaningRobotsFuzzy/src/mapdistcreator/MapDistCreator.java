package mapdistcreator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MapDistCreator {
    public static void main(String[] args) {
        Random random = new Random();

        // Create 90 files
        for (int i = 1; i <= 90; i++) {
            String fileName = "MapDist" + i + ".txt";

            // Create the file and write 200 random values
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (int j = 0; j < 200; j++) {
                    double randomValue;
                    // Generate random double values based on the specified ranges
                    if (i >= 1 && i <= 30) {
                        // Range: 0 to 100
                        randomValue = random.nextDouble() * 100;
                    } else if (i >= 31 && i <= 60) {
                        // Range: 30 to 100
                        randomValue = 30 + random.nextDouble() * (100 - 30);
                    } else {
                        // Range: 50 to 100
                        randomValue = 50 + random.nextDouble() * (100 - 50);
                    }
                    writer.write(String.valueOf(randomValue));
                    writer.newLine(); // Add a new line after each number
                }
                System.out.println("File '" + fileName + "' created with 200 values.");
            } catch (IOException e) {
                System.err.println("Error writing to file '" + fileName + "': " + e.getMessage());
            }
        }
    }
}
