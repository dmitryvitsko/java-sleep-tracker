package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SleepTrackerAppTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private SleepingSession createSession(String start, String end, SleepQuality quality) {
        return new SleepingSession(
                LocalDateTime.parse(start, FORMATTER),
                LocalDateTime.parse(end, FORMATTER),
                quality
        );
    }

    @Test
    public void testTotalSessionsFunction() {
        TotalSessionsFunction function = new TotalSessionsFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                createSession("02.10.25 14:00", "02.10.25 15:00", SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(2, result.getFunctionResult());
    }

    @Test
    public void testSleeplessNightsZeroWhenSleptEveryNight() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                createSession("03.10.25 01:30", "03.10.25 08:00", SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getFunctionResult());
    }

    @Test
    public void testSleeplessNightsWithDaySleep() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 23:00", "02.10.25 07:00", SleepQuality.GOOD),
                createSession("03.10.25 13:00", "03.10.25 15:00", SleepQuality.NORMAL),
                createSession("03.10.25 23:30", "04.10.25 07:30", SleepQuality.GOOD)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(1L, result.getFunctionResult());
    }

    @Test
    public void testSleeplessNightsWhenFirstSessionStartsBeforeNoon() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 09:00", "01.10.25 14:00", SleepQuality.BAD)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(1L, result.getFunctionResult());
    }

    @Test
    public void testChronotypeOwl() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 23:30", "02.10.25 09:30", SleepQuality.GOOD),
                createSession("03.10.25 01:00", "03.10.25 10:00", SleepQuality.GOOD),
                createSession("03.10.25 22:30", "04.10.25 07:30", SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.СОВА, result.getFunctionResult());
    }

    @Test
    public void testChronotypeLark() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 21:30", "02.10.25 06:30", SleepQuality.GOOD),
                createSession("02.10.25 21:00", "03.10.25 06:00", SleepQuality.GOOD)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.ЖАВОРОНОК, result.getFunctionResult());
    }

    @Test
    public void testChronotypePigeon() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 23:40", "02.10.25 09:30", SleepQuality.GOOD),
                createSession("02.10.25 21:30", "03.10.25 06:30", SleepQuality.GOOD)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.ГОЛУБЬ, result.getFunctionResult());
    }

    @Test
    public void testChronotypePigeonWhenTieBetweenOwlAndPigeon() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 23:40", "02.10.25 09:30", SleepQuality.GOOD),
                createSession("02.10.25 23:30", "03.10.25 07:30", SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.ГОЛУБЬ, result.getFunctionResult());
    }

    @Test
    public void testChronotypePigeonWhenTieBetweenLarkAndPigeon() {
        ChronotypeFunction function = new ChronotypeFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 21:30", "02.10.25 06:30", SleepQuality.GOOD),
                createSession("02.10.25 22:30", "03.10.25 07:30", SleepQuality.NORMAL)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(Chronotype.ГОЛУБЬ, result.getFunctionResult());
    }

    @Test
    public void testSleeplessNightsWithMultipleSessionsInOneNight() {
        SleeplessNightsFunction function = new SleeplessNightsFunction();
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 23:00", "02.10.25 02:00", SleepQuality.BAD),
                createSession("02.10.25 03:00", "02.10.25 06:00", SleepQuality.NORMAL),
                createSession("02.10.25 23:00", "03.10.25 07:00", SleepQuality.GOOD)
        );

        SleepAnalysisResult result = function.apply(sessions);
        Assertions.assertEquals(0L, result.getFunctionResult());
    }

    @Test
    public void testMinMaxAverageAndBadQualityFunctions() {
        List<SleepingSession> sessions = List.of(
                createSession("01.10.25 14:00", "01.10.25 15:00", SleepQuality.BAD),
                createSession("01.10.25 23:00", "02.10.25 02:00", SleepQuality.GOOD)
        );

        MinDurationFunction minDurationFunction = new MinDurationFunction();
        MaxDurationFunction maxDurationFunction = new MaxDurationFunction();
        AverageDurationFunction averageDurationFunction = new AverageDurationFunction();
        BadQualitySessionsFunction badQualitySessionsFunction = new BadQualitySessionsFunction();

        Assertions.assertEquals(60L, minDurationFunction.apply(sessions).getFunctionResult());
        Assertions.assertEquals(180L, maxDurationFunction.apply(sessions).getFunctionResult());
        Assertions.assertEquals(120L, averageDurationFunction.apply(sessions).getFunctionResult());
        Assertions.assertEquals(1L, badQualitySessionsFunction.apply(sessions).getFunctionResult());
    }
}