package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime timeToFallAsleep;
    private final LocalDateTime wakeUpTime;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime timeToFallAsleep, LocalDateTime wakeUpTime, SleepQuality sleepQuality) {
        this.timeToFallAsleep = timeToFallAsleep;
        this.wakeUpTime = wakeUpTime;
        this.sleepQuality = sleepQuality;
    }

    public LocalDateTime getTimeToFallAsleep() {
        return timeToFallAsleep;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    public LocalDateTime getWakeUpTime() {
        return wakeUpTime;
    }

    public long getDurationInMinutes() {
        return Duration.between(timeToFallAsleep, wakeUpTime).toMinutes();
    }
}
