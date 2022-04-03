import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private final String id;
    private String type;
    private String host;
    private State state;
    private long timeStarted;
    private long timeFinished;

    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public Event(String id, String type, String host, State state, long timeStarted, long timeFinished) {
        this.id = id;
        this.type = type;
        this.host = host;
        this.state = state;
        this.timeStarted = timeStarted;
        this.timeFinished = timeFinished;
    }

    public Event(String id, State state, long timeStarted, long timeFinished) {
        this(id, null, null, state, timeStarted, timeFinished);
    }

    public void setStartTime(long timestamp) {
        timeStarted = timestamp;
    }

    public void setEndTime(long timestamp) {
        timeFinished = timestamp;
    }

    public boolean hasCompletedDetails() {
        return timeStarted > 0 && timeFinished > 0;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public long getTimeFinished() {
        return timeFinished;
    }

    @Override
    public String toString() {
        return String.format("ID: %10s State: %10s Type: %15s HOST: %15s  Started: %20s Finished: %20s", id, state, type, host, formatter.format(new Date(timeStarted)), formatter.format(new Date(timeFinished)));
    }
}
