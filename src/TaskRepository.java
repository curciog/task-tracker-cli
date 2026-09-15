import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
                                "\"description\": \"" + task.getDescription() + "\",\n" +
                                "\"status\": \"" + task.getStatus() + "\",\n" +
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
}
