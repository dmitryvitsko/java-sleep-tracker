package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.function.Function;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0);
        }
        LocalDateTime firstSession = sleepingSessions.get(0).getTimeToFallAsleep();
        LocalDate lastNightDate = sleepingSessions.get(sleepingSessions.size() - 1).getWakeUpTime().toLocalDate().plusDays(1);

        LocalDate firstNightDate;

        if (firstSession.getHour() < 12) {
            firstNightDate = firstSession.toLocalDate();
        } else {
            firstNightDate = firstSession.toLocalDate().plusDays(1);
        }

        int totalNights = Period.between(firstNightDate, lastNightDate).getDays();


    }
}
