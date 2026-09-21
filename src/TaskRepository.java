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

    private static final Pattern TASK_PATTERN = Pattern.compile(
            "\\{\\s*" +
                    "\"id\":\\s*(-?\\d+),\\s*" +
                    "\"description\":\\s*\"((?:\\\\.|[^\"\\\\])*)\",\\s*" +
                    "\"status\":\\s*\"([^\"]*)\",\\s*" +
                    "\"createdAt\":\\s*\"([^\"]*)\",\\s*" +
                    "\"updatedAt\":\\s*\"([^\"]*)\"\\s*" +
                    "\\}"
    );

    public void createFileIfNotExists() {

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IllegalArgumentException("Error creating tasks.json.");
        }
    }

    public void save(List<Task> tasks) {

        try (FileWriter writer = new FileWriter(file)) {

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
        } catch (IOException e) {
            throw new IllegalArgumentException("Error while saving tasks.json.");
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

        StringBuilder content = new StringBuilder();

        try (Scanner sc = new Scanner(file)) {

            while (sc.hasNextLine()) {
                content.append(sc.nextLine());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading tasks.json.");
        }

        return content.toString();
    }

    public List<Task> findAll() {

        List<Task> tasks = new ArrayList<>();

        String content = read().trim();

        if (content.isBlank()) {
            return tasks;
        }

        String trimmedContent = content.trim();

        if (trimmedContent.equals("[]")) {
            return tasks;
        }

        Matcher matcher = TASK_PATTERN.matcher(content);

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

            Task task = parseTask(matcher, tasks);
            tasks.add(task);

            lastEnd = matcher.end();
        }

        String afterLastTask = content.substring(lastEnd, content.length() - 1);

        if (!afterLastTask.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid tasks.json format.");
        }

        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Invalid tasks.json format.");
        }

        return tasks;
    }

    private Task parseTask(Matcher matcher, List<Task> tasks) {

        String description = unescapeJson(matcher.group(2));

        int id;

        try {
            id = Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid task ID in tasks.json.");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid task ID in tasks.json.");
        }

        TaskStatus status;

        try {
            status = TaskStatus.valueOf(
                    matcher.group(3).toUpperCase().replace("-", "_")
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid task status in tasks.json.");
        }

        LocalDateTime createdAt;
        LocalDateTime updatedAt;

        try {
            createdAt = LocalDateTime.parse(matcher.group(4));
            updatedAt = LocalDateTime.parse(matcher.group(5));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid task date in tasks.json.");
        }

        for (Task existingTask : tasks) {

            if (existingTask.getId() == id) {
                throw new IllegalArgumentException("Duplicate task ID in tasks.json.");
            }
        }

        return new Task(id, description, status, createdAt, updatedAt);
    }
}
