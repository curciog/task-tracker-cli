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

        for (Task task : tasks) {

            if (task.getId() == id) {

                task.setDescription(description);
                task.setUpdatedAt(java.time.LocalDateTime.now());

                repository.save(tasks);

                System.out.println("Task updated.");
                return;
            }
        }

        System.out.println("Task not found.");
    }

    public void markInProgress(int id) {

        List<Task> tasks = repository.findAll();

        for (Task task : tasks) {

            if (task.getId() == id) {

                task.setStatus(TaskStatus.IN_PROGRESS);
                task.setUpdatedAt(java.time.LocalDateTime.now());

                repository.save(tasks);

                System.out.println("Task marked as in-progress.");
                return;
            }
        }

        System.out.println("Task not found.");
    }

    public void markDone(int id) {

        List<Task> tasks = repository.findAll();

        for (Task task : tasks) {

            if (task.getId() == id) {

                task.setStatus(TaskStatus.DONE);
                task.setUpdatedAt(java.time.LocalDateTime.now());

                repository.save(tasks);

                System.out.println("Task marked as done.");
                return;
            }
        }

        System.out.println("Task not found.");
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
}
