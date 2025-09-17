package util;

import model.Task;
import java.io.*;
import java.util.*;

public class CSVLoader {
    public static List<Task> loadTasks(String filePath) {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // skip blank lines

                // Skip header row
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }

                tasks.add(new Task(
                        parts[0].trim(),
                        Integer.parseInt(parts[1].trim()),
                        Integer.parseInt(parts[2].trim()),
                        Integer.parseInt(parts[3].trim()),
                        Integer.parseInt(parts[4].trim())
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
