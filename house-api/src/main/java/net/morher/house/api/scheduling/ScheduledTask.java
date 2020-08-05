package net.morher.house.api.scheduling;

/**
 * A handle for a scheduled task. The task can be canceled before it's execution starts.
 * 
 * @author Morten Hermansen
 */
public interface ScheduledTask {
    /**
     * If the task is not already started, cancel it.
     */
    void cancel();

    boolean isCancelled();

    boolean isWaiting();

    boolean hasRun();
}
