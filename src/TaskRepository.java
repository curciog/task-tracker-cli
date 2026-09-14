import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void save(Task task) {

        try {
            FileWriter writer = new FileWriter(file);

            writer.write(
                    "{\n" +
                            "\"id\": " + task.getId() + ",\n" +
                            "\"description\": \"" + task.getDescription() + "\",\n" +
                            "\"status\": \"" + task.getStatus() + "\",\n" +
                            "\"createdAt\": \"" + task.getCreatedAt() + "\",\n" +
                            "\"updatedAt\": \"" + task.getUpdatedAt() + "\"\n" +
                            "}"
            );

            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving task.");
        }
    }
}
