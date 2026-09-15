import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int nextId = 1;

    public static void main(String[] args) {

        TaskRepository repository = new TaskRepository();

        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task(1, "Study smth", TaskStatus.TODO));
        tasks.add(new Task(2, "Study anyth", TaskStatus.TODO));

        repository.save(tasks);
    }
}
