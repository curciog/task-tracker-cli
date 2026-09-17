public class Main {

    public static void main(String[] args) {

        TaskRepository repository = new TaskRepository();
        TaskService service = new TaskService(repository);

        if (args[0].equals("add")) {
            service.addTask(args[1]);
        }

        if (args[0].equals("delete")) {
            int id = Integer.parseInt(args[1]);
            service.deleteTask(id);
        }

        if (args[0].equals("update")) {
            int id = Integer.parseInt(args[1]);
            service.updateTask(id, args[2]);
        }

        if (args[0].equals("mark-in-progress")) {
            int id = Integer.parseInt(args[1]);
            service.markInProgress(id);
        }

        if (args[0].equals("mark-done")) {
            int id = Integer.parseInt(args[1]);
            service.markDone(id);
        }

        if (args[0].equals("list")) {
            service.listTasks();
        }
    }
}
