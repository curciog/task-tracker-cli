public class Main {

    public static void main(String[] args) {

        TaskRepository repository = new TaskRepository();

        TaskService service = new TaskService(repository);

        service.addTask("Study Spring");
    }
}
