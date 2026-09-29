package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {


    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        long numberSleepingNights = sleepingSessions.stream()
                .filter(session -> !session.getWakeUpTime().toLocalDate()
                        .equals(session.getTimeToFallAsleep().toLocalDate())
                        || session.getTimeToFallAsleep().getHour() < 6)
                .count();

        long numberSleepingNightsOwl = sleepingSessions.stream()
                .filter(session -> !session.getWakeUpTime().toLocalDate()
                        .equals(session.getTimeToFallAsleep().toLocalDate())
                        || session.getTimeToFallAsleep().getHour() < 6)
                .filter(session -> (session.getTimeToFallAsleep().toLocalTime().isAfter(LocalTime.of(23, 0))
                        || session.getTimeToFallAsleep().toLocalTime().isBefore(LocalTime.of(6, 0)))
                        && session.getWakeUpTime().toLocalTime().isAfter(LocalTime.of(9, 0)))
                .count();

        long numberSleepingNightsLark = sleepingSessions.stream()
                .filter(session -> !session.getWakeUpTime().toLocalDate()
                        .equals(session.getTimeToFallAsleep().toLocalDate())
                        || session.getTimeToFallAsleep().getHour() < 6)
                .filter(session -> (session.getTimeToFallAsleep().toLocalTime().isAfter(LocalTime.of(6, 0))
                        && session.getTimeToFallAsleep().toLocalTime().isBefore(LocalTime.of(22, 0)))
                        && session.getWakeUpTime().toLocalTime().isBefore(LocalTime.of(7, 0)))
                .count();

        long numberSleepingNightsPigeon = numberSleepingNights - (numberSleepingNightsLark + numberSleepingNightsOwl);

        Chronotype resultType;
        if (numberSleepingNightsOwl > numberSleepingNightsLark
                && numberSleepingNightsOwl > numberSleepingNightsPigeon) {
            resultType = Chronotype.СОВА;
        } else if (numberSleepingNightsLark > numberSleepingNightsOwl
                && numberSleepingNightsLark > numberSleepingNightsPigeon) {
            resultType = Chronotype.ЖАВОРОНОК;
        } else {
            resultType = Chronotype.ГОЛУБЬ;
        }

        return new SleepAnalysisResult("Ваш хронотип", resultType);
    }
}
