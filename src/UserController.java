import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    private List<User> users;

    public UserController() {
        this.users = new ArrayList<>();
        // Add some sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Sample evaluators
        users.add(new User("E001", "Dr. Ahmad", UserRole.EVALUATOR));
        users.add(new User("E002", "Dr. Lim", UserRole.EVALUATOR));
        users.add(new User("E003", "Dr. Muthu", UserRole.EVALUATOR));

        // Sample students
        users.add(new User("S001", "Ali bin Abu", UserRole.STUDENT));
        users.add(new User("S002", "Tan Wei Ming", UserRole.STUDENT));
        users.add(new User("S003", "Priya Devi", UserRole.STUDENT));
        users.add(new User("S004", "John Lee", UserRole.STUDENT));
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public List<User> getEvaluators() {
        return users.stream()
                .filter(user -> user.getRole() == UserRole.EVALUATOR)
                .collect(Collectors.toList());
    }

    public List<User> getStudents() {
        return users.stream()
                .filter(user -> user.getRole() == UserRole.STUDENT)
                .collect(Collectors.toList());
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
