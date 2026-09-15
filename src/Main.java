public class Main {

    public static void main(String[] args) {

        TaskRepository repository = new TaskRepository();
        TaskService service = new TaskService(repository);

        if (args[0].equals("add")) {
            service.addTask(args[1]);
        }
    }
}
