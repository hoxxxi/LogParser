import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LogAnalyser {
    private final long PUBLISHING_THRESHOLD = 4;
    Map<String, Event> eventMap;

    public LogAnalyser() {
        eventMap = new HashMap<>();
    }

    public void process(File eventsFile) {
        try {
            Scanner scanner = new Scanner(eventsFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Event event = parseJSON(line);
                    if(event.hasCompletedDetails()) {
                        eventMap.remove(event.getId());
                        event.setState(State.FINISHED);
                        if(!acceptableLatency(event)) {
                            publishLatencyViolation(event);
                        }
                    }
                } catch (JSONException jsonException) {
                    System.err.println("ERROR processing line :" + line);
                }

            }
        } catch (IOException inputException) {
            inputException.printStackTrace();
        }
    }

    boolean acceptableLatency(Event event) {
        return event.getTimeFinished() - event.getTimeStarted() < PUBLISHING_THRESHOLD;
    }

    Event parseJSON(String line) {
        JSONObject obj = new JSONObject(line);
        String id = obj.getString("id");
        State state = obj.getEnum(State.class, "state");
        long timestamp = obj.getLong("timestamp");
        String type = null;
        String host = null;
        try {
            type = obj.getString("type");
        } catch (JSONException jsonException) {
            if(!jsonException.getMessage().equals("JSONObject[\"type\"] not found."))
                throw jsonException;
        }
        try {
            host = obj.getString("host");
        } catch (JSONException jsonException) {
            if(!jsonException.getMessage().equals("JSONObject[\"host\"] not found."))
                throw jsonException;
        }

        Event event = eventMap.get(id);

        if(state == State.STARTED) {
            if(event == null) {
                event = new Event(id, state, timestamp, 0);
                eventMap.put(id, event);
            } else {
                event.setStartTime(timestamp);
            }
        }

        if(state == State.FINISHED) {
            if(event == null) {
                event = new Event(id, state,0, timestamp);
                eventMap.put(id, event);
            } else {
                event.setEndTime(timestamp);
            }
        }

        if(event.getType() == null)
            event.setType(type);

        if(event.getHost() == null)
            event.setHost(host);

        return event;
    }

    private void publishLatencyViolation(Event event) {
        //depending on how we want to handle violations we can pass the event object to any publisher we want
        System.out.println(event);
    }

    public Event getEvent(String eventID) {
        return eventMap.get(eventID);
    }
}
