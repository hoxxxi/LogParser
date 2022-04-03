import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogAnalyserTest {

    @Test
    public void testParseJSON() {
        LogAnalyser analyser = new LogAnalyser();

        String message = "{\"id\":\"TEST_ID\", \"state\":\"STARTED\", \"type\":\"TEST_TYPE\", \"host\":\"TEST_HOST\", \"timestamp\":1500000000000}";
        Event event = analyser.parseJSON(message);
        assertEquals("TEST_ID", event.getId());
        assertEquals(State.STARTED, event.getState());
        assertEquals("TEST_TYPE", event.getType());
        assertEquals("TEST_HOST", event.getHost());
        assertEquals(1500000000000L, event.getTimeStarted());
        assertEquals(0L, event.getTimeFinished());

        message = "{\"id\":\"TEST_ID\", \"state\":\"FINISHED\", \"type\":\"TEST_TYPE\", \"host\":\"TEST_HOST\", \"timestamp\":1500000000005}";
        analyser.parseJSON(message);
        event = analyser.getEvent("TEST_ID");
        assertEquals("TEST_ID", event.getId());
        assertEquals(State.STARTED, event.getState());
        assertEquals("TEST_TYPE", event.getType());
        assertEquals("TEST_HOST", event.getHost());
        assertEquals(1500000000000L, event.getTimeStarted());
        assertEquals(1500000000005L, event.getTimeFinished());
        assertTrue(event.hasCompletedDetails());
        assertFalse(analyser.acceptableLatency(event));
    }
}
