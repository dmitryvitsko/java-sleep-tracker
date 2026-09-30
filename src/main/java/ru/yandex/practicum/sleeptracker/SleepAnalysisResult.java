package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String description;
    private final Object functionResult;

    public SleepAnalysisResult(String description, Object functionResult) {
        this.description = description;
        this.functionResult = functionResult;
    }

    public String getDescription() {
        return description;
    }

    public Object getFunctionResult() {
        return functionResult;
    }

    @Override
    public String toString() {
        return getDescription() + " - " + getFunctionResult();
    }
}
