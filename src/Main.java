public class Main {

    public static void main(String[] args) {

        TaskRepository repository = new TaskRepository();
        TaskService service = new TaskService(repository);

        try {
            repository.createFileIfNotExists();

            if (args.length == 0) {
                System.out.println("No command provided.");
                return;
            }

            executeCommand(args, service);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void executeCommand(String[] args, TaskService service) {

        switch (args[0]) {

            case "add": {
                validateAddArguments(args);
                service.addTask(args[1]);
                break;
            }

            case "update": {
                validateUpdateArguments(args);
                service.updateTask(parseTaskId(args[1]), args[2]);
                break;
            }

            case "delete": {
                validateTaskIdArguments(args);
                service.deleteTask(parseTaskId(args[1]));
                break;
            }

            case "mark-in-progress": {
                validateTaskIdArguments(args);
                service.markInProgress(parseTaskId(args[1]));
                break;
            }

            case "mark-done": {
                validateTaskIdArguments(args);
                service.markDone(parseTaskId(args[1]));
                break;
            }

            case "list": {
                executeListCommand(args, service);
                break;
            }

            default:
                System.out.println("Unknown command.");
        }
    }

    private static void validateAddArguments(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException("Description is required.");
        }

        if (args.length > 2) {
            throw new IllegalArgumentException("Too many arguments for add command.");
        }

        if (args[1].isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
    }

    private static void validateUpdateArguments(String[] args) {

        if (args.length < 3) {
            throw new IllegalArgumentException("Task ID and description are required.");
        }

        if (args.length > 3) {
            throw new IllegalArgumentException("Too many arguments for update command.");
        }

        if (args[2].isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
    }

    private static void validateTaskIdArguments(String[] args) {

        if (args.length < 2) {
            throw new IllegalArgumentException("Task ID is required.");
        }

        if (args.length > 2) {
            throw new IllegalArgumentException("Too many arguments for " + args[0] + " command.");
        }
    }

    private static int parseTaskId(String value) {

        try {
            int id = Integer.parseInt(value);

            if (id <= 0) {
                throw new IllegalArgumentException("Task ID must be a positive number.");
            }

            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Task ID must be a number.");
        }
    }

    private static void executeListCommand(String[] args, TaskService service) {

        if (args.length == 1) {
            service.listTasks(null);
            return;
        }

        if (args.length > 2) {
            System.out.println("Too many arguments for list command.");
            return;
        }

        switch (args[1]) {

            case "done": {
                service.listTasks(TaskStatus.DONE);
                break;
            }

            case "todo": {
                service.listTasks(TaskStatus.TODO);
                break;
            }

            case "in-progress": {
                service.listTasks(TaskStatus.IN_PROGRESS);
                break;
            }

            default:
                System.out.println("Invalid list filter.");
        }
    }
}

