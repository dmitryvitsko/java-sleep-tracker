package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SleepTrackerApp {
    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> FUNCTION_LIST = List.of(
            new TotalSessionsFunction(),
            new MinDurationFunction(),
            new MaxDurationFunction(),
            new AverageDurationFunction(),
            new BadQualitySessionsFunction(),
            new SleeplessNightsFunction()
    );
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static SleepingSession parseLine(String line) {
        String[] array = line.split(";");
        LocalDateTime timeToFallAsleep = LocalDateTime.parse(array[0], DATE_TIME_FORMATTER);
        LocalDateTime wakeUpTime = LocalDateTime.parse(array[1], DATE_TIME_FORMATTER);
        SleepQuality sleepQuality = SleepQuality.valueOf(array[2]);

        return new SleepingSession(timeToFallAsleep, wakeUpTime, sleepQuality);
    }

    private static List<SleepingSession> listOfSleepSessions = new ArrayList<>();


    public static void main(String[] args) {
        try (Stream<String> lines = Files.lines(Paths.get(args[0]))) {
            listOfSleepSessions = lines.map(SleepTrackerApp::parseLine)
                    .toList();
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return;
        }

        FUNCTION_LIST.stream()
                .map(function -> function.apply(listOfSleepSessions))
                .forEach(System.out::println);
    }
}