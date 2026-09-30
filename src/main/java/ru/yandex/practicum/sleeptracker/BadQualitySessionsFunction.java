package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult>  {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long badSessions = sleepingSessions.stream()
                .filter(session -> session.getSleepQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult("Количество сессий сна с плохим качеством сна", badSessions);
    }
}
