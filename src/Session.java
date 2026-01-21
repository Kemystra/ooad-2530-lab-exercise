public class Session {
    private String date;
    private String venue;
    private SessionType type;
    private User evaluator;
    private User student;

    public Session(String date, String venue, SessionType type, User evaluator, User student) {
        this.date = date;
        this.venue = venue;
        this.type = type;
        this.evaluator = evaluator;
        this.student = student;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public SessionType getType() {
        return type;
    }

    public User getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(User evaluator) {
        this.evaluator = evaluator;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
}
