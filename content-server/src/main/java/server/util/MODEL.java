package server.util;

public enum MODEL {
    COURSE("course"), BLOG("blog"), CONCEPT("concept"), TOOL("tool"), PROJECT("project");

    private String operation;

    private MODEL(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation;
    }
}
