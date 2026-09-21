public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    public String toDisplayName() {
        return name().toLowerCase().replace("_", "-");
    }
}