import java.util.List;

public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public void addTask(String description) {

        List<Task> tasks = repository.findAll();

        int nextId = 1;

        for (Task task : tasks) {
            if (task.getId() >= nextId) {
                nextId = task.getId() + 1;
            }
        }

        Task task = new Task(nextId, description, TaskStatus.TODO);

        tasks.add(task);

        repository.save(tasks);
    }
}
