package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.function.Function;
import java.time.temporal.ChronoUnit;

public class SleeplessNightsFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        LocalDateTime firstSession = sleepingSessions.get(0).getTimeToFallAsleep();
        LocalDate lastNightDate = sleepingSessions.get(sleepingSessions.size() - 1)
                .getWakeUpTime()
                .toLocalDate()
                .plusDays(1);

        LocalDate firstNightDate;
        if (firstSession.getHour() < 12) {
            firstNightDate = firstSession.toLocalDate();
        } else {
            firstNightDate = firstSession.toLocalDate().plusDays(1);
        }

        long totalNights = ChronoUnit.DAYS.between(firstNightDate, lastNightDate);

        long numberSleepingNights = sleepingSessions.stream()
                .filter(session -> !session.getWakeUpTime().toLocalDate()
                        .equals(session.getTimeToFallAsleep().toLocalDate())
                        || session.getTimeToFallAsleep().getHour() < 6)
                .map(session -> session.getWakeUpTime().toLocalDate())
                .distinct()
                .count();

        long sleeplessNights = totalNights - numberSleepingNights;

        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNights);
    }
}