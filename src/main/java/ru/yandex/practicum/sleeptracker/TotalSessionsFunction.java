package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class TotalSessionsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> list) {
        int numberOfSleepSessions = list.size();
        return new SleepAnalysisResult("Всего сессий сна", numberOfSleepSessions);
    }
}
