import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskRepository {

    private final File file = new File("tasks.json");

    public void createFileIfNotExists() {

        try {
            if (file.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error creating file.");
        }
    }

    public void save(List<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(file);

            writer.write("[\n");

            for (int i = 0; i < tasks.size(); i++) {

                Task task = tasks.get(i);

                writer.write(
                        "{\n" +
                                "\"id\": " + task.getId() + ",\n" +
                                "\"description\": \"" + escapeJson(task.getDescription()) + "\",\n" +
                                "\"status\": \"" + task.getStatus().name().toLowerCase().replace("_","-") + "\",\n" +
                                "\"createdAt\": \"" + task.getCreatedAt() + "\",\n" +
                                "\"updatedAt\": \"" + task.getUpdatedAt() + "\"\n" +
                                "}"
                );

                if (i < tasks.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");

            writer.close();

        } catch (IOException e) {
            System.out.println("Error while saving task.");
        }
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }

    private String unescapeJson(String value) {
        return value
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    public String read() {
        try {
            Scanner sc = new Scanner(file);
            StringBuilder content = new StringBuilder();

            while (sc.hasNextLine()) {
                content.append(sc.nextLine());
            }
            sc.close();

            return content.toString();
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return "";
        }
    }

    public List<Task> findAll() {

        List<Task> tasks = new ArrayList<>();

        String content = read();

        if (content.isBlank()) {
            return tasks;
        }

        if (content.trim().equals("[]")) {
            return tasks;
        }

        Pattern pattern = Pattern.compile(
                "\\{\\s*" +
                        "\"id\":\\s*(-?\\d+),\\s*" +
                        "\"description\":\\s*\"((?:\\\\.|[^\"\\\\])*)\",\\s*" +
                        "\"status\":\\s*\"([^\"]*)\",\\s*" +
                        "\"createdAt\":\\s*\"([^\"]*)\",\\s*" +
                        "\"updatedAt\":\\s*\"([^\"]*)\"\\s*" +
                        "\\}"
        );

        Matcher matcher = pattern.matcher(content);

        String trimmedContent = content.trim();

        if (!trimmedContent.startsWith("[") || !trimmedContent.endsWith("]")) {
            throw new IllegalArgumentException("Invalid tasks.json format.");
        }

        int lastEnd = 1;
        boolean firstTask = true;

        while (matcher.find()) {

            String between = content.substring(lastEnd, matcher.start());

            if (firstTask) {

                if (!between.trim().isEmpty()) {
                    throw new IllegalArgumentException("Invalid tasks.json format.");
                }

                firstTask = false;
            } else {

                if (!between.trim().equals(",")) {
                    throw new IllegalArgumentException("Invalid tasks.json format.");
                }
            }

            String description = unescapeJson(matcher.group(2));

            int id;

            try{
                id = Integer.parseInt(matcher.group(1));

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid task ID in tasks.json");
            }

            if (id <= 0) {
                throw new IllegalArgumentException("Invalid task ID in tasks.json");
            }

            TaskStatus status;
            LocalDateTime createdAt;
            LocalDateTime updatedAt;

            try {

                status = TaskStatus.valueOf(
                        matcher.group(3).toUpperCase().replace("-", "_"));

                createdAt = LocalDateTime.parse(matcher.group(4));

                updatedAt = LocalDateTime.parse(matcher.group(5));

            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid task status in tasks.json.");

            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid task date in tasks.json.");
            }

            for (Task existingTask : tasks) {

                if (existingTask.getId() == id) {
                    throw new IllegalArgumentException("Duplicate task ID in tasks.json");
                }
            }

            Task task = new Task(id, description, status, createdAt, updatedAt);

            tasks.add(task);

            lastEnd = matcher.end();
        }

        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Invalid tasks.json format.");
        }

        return tasks;
    }
}
