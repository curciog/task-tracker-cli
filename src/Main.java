public class Main {

    public static void main(String[] args) {

        TaskRepository repository = new TaskRepository();
        repository.createFileIfNotExists();

        TaskService service = new TaskService(repository);

        if (args.length == 0) {
            System.out.println("No command provided.");
            return;
        }

        if (args[0].equals("add") && args.length < 2) {
            System.out.println("Description is required.");
            return;
        }

        if (args[0].equals("update") && args.length < 3) {
            System.out.println("Task ID and description are required.");
            return;
        }

        if ((args[0].equals("delete")
                || args[0].equals("update")
                || args[0].equals("mark-in-progress")
                || args[0].equals("mark-done"))
                && args.length < 2) {

            System.out.println("Task ID is required.");
            return;
        }

        try {
            executeCommand(args, service);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void executeCommand(String[] args, TaskService service) {

        boolean validCommand = false;

        if (args[0].equals("add")) {
            validCommand = true;
            service.addTask(args[1]);
        }

        if (args[0].equals("delete")) {

            try {
                validCommand = true;
                int id = Integer.parseInt(args[1]);
                service.deleteTask(id);
            } catch (NumberFormatException e) {
                System.out.println("Task ID must be a number.");
            }
        }

        if (args[0].equals("update")) {

            try {
                validCommand = true;
                int id = Integer.parseInt(args[1]);
                service.updateTask(id, args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Task ID must be a number.");
            }
        }

        if (args[0].equals("mark-in-progress")) {

            try {
                validCommand = true;
                int id = Integer.parseInt(args[1]);
                service.markInProgress(id);
            } catch (NumberFormatException e) {
                System.out.println("Task ID must be a number.");
            }
        }

        if (args[0].equals("mark-done")) {

            try {
                validCommand = true;
                int id = Integer.parseInt(args[1]);
                service.markDone(id);
            } catch (NumberFormatException e) {
                System.out.println("Task ID must be a number.");
            }
        }

        if (args[0].equals("list")) {

            validCommand = true;

            if (args.length == 1) {
                service.listTasks(null);
            }

            if (args.length == 2) {

                if(args[1].equals("done")) {
                    service.listTasks(TaskStatus.DONE);
                } else if(args[1].equals("todo")) {
                    service.listTasks(TaskStatus.TODO);
                } else if(args[1].equals("in-progress")) {
                    service.listTasks(TaskStatus.IN_PROGRESS);
                } else {
                    System.out.println("Invalid list filter.");
                }
            }
        }

        if (!validCommand)
            System.out.println("Unknown command.");
    }
}
