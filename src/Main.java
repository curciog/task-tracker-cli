public class Main {

    private static int nextId = 1;

    public static void main(String[] args) {
        
        TaskRepository repository = new TaskRepository();

        Task task = new Task(1, "Estudar Java", TaskStatus.TODO);
        repository.save(task);
    }

    public static void addTask(String desc) {

        Task task = new Task(nextId, desc, TaskStatus.TODO);
        nextId++;

        System.out.println("Task created:");
        System.out.println("ID: " + task.getId());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Status: " + task.getStatus());
    }
}
