package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Long duration = (long) sleepingSessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult("Средняя продолжительность сессии сна", duration);
    }
}
