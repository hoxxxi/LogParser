# LogParser

Simple log parser processing JSON messages from a text file and publishing all messages with latency above a threshold of 4ms.

To run the project you need to build it using gradle and run with the log file as argument (Example: ./gradlew run --args="src/main/resources/logfile.txt").
