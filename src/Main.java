import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int nextId = 1;

    public static void main(String[] args) {

        TaskRepository repository = new TaskRepository();

        var tasks = repository.findAll();

        for (Task task : tasks) {
            System.out.println("ID: " + task.getId());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Status: " + task.getStatus());
            System.out.println("Created at: " + task.getCreatedAt());
            System.out.println("Updated at: " + task.getUpdatedAt() + "\n");
        }
    }
}
