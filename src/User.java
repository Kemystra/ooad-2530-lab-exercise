public class User {
    private String id;
    private String name;
    private UserRole role;

    public User(String id, String name, UserRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
