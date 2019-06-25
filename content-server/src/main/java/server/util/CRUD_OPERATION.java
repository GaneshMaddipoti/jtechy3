package server.util;

public enum CRUD_OPERATION {
    LIST("list"), ADD("add"), MODIFY("modify"), DELETE("delete"), SAVE("save"), VIEW("view");

    private String operation;

    private CRUD_OPERATION(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation;
    }
}
