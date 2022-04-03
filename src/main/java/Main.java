import java.io.File;

public class Main {
    public static void main(String[] args) {
//        File eventsFile = new File(Main.class.getResource("logFile.txt").getPath());
        File eventsFile = new File(args[0]);
        LogAnalyser parser = new LogAnalyser();
        parser.process(eventsFile);
    }
}
