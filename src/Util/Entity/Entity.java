package Util.Entity;

abstract public class Entity {
    private String name;

    public Entity(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
