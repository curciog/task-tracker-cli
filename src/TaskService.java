import java.time.LocalDateTime;
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

                if (task.getId() == Integer.MAX_VALUE) {
                    throw new IllegalArgumentException("Cannot create task: maximum task ID reached.");
                }
                nextId = task.getId() + 1;
            }
        }

        Task task = new Task(nextId, description, TaskStatus.TODO);

        tasks.add(task);

        repository.save(tasks);

        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    public void deleteTask(int id) {

        List<Task> tasks = repository.findAll();

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                repository.save(tasks);
                System.out.println("Task deleted.");
                return;
            }
        }

        System.out.println("Task not found.");
    }

    public void updateTask(int id, String description) {

        List<Task> tasks = repository.findAll();

        Task task = findTaskById(tasks, id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        task.setDescription(description);
        task.setUpdatedAt(LocalDateTime.now());

        repository.save(tasks);

        System.out.println("Task updated.");
    }

    public void markInProgress(int id) {

        List<Task> tasks = repository.findAll();

        Task task = findTaskById(tasks, id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setUpdatedAt(LocalDateTime.now());

        repository.save(tasks);

        System.out.println("Task marked as in-progress.");
    }

    public void markDone(int id) {

        List<Task> tasks = repository.findAll();

        Task task = findTaskById(tasks, id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        task.setStatus(TaskStatus.DONE);
        task.setUpdatedAt(LocalDateTime.now());

        repository.save(tasks);

        System.out.println("Task marked as done.");
    }

    public void listTasks(TaskStatus status) {

        List<Task> tasks = repository.findAll();

        for (Task task : tasks) {

            if (status == null || task.getStatus() == status) {

                System.out.println("ID: " + task.getId());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Status: " + task.getStatus().name().toLowerCase().replace("_","-"));
                System.out.println("Created at: " + task.getCreatedAt());
                System.out.println("Updated at: " + task.getUpdatedAt() + "\n");
            }
        }
    }

    private Task findTaskById(List<Task> tasks, int id) {

        for (Task task : tasks) {

            if (task.getId() == id) {
                return task;
            }
        }

        return null;
    }
}
