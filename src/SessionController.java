import java.util.List;

public class SessionController {
    private List<Session> sessions;

    public SessionController() {
        this.sessions = new java.util.ArrayList<Session>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
