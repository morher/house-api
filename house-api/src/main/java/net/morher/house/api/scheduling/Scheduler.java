package net.morher.house.api.scheduling;

import java.util.concurrent.TimeUnit;

/**
 * Interface for a scheduling mechanism able to run a task at a specific time. Scheduled tasks are not guaranteed to run at the
 * specified time, it is added to the {@link TaskQueue} to be run as soon as possible.
 * 
 * @author Morten Hermansen
 */
public interface Scheduler extends TaskQueue {

    /**
     * Schedule a task to be run after a given amount of time. Once the duration has past the task will be added to the task
     * queue, just like a task registered with {@link TaskQueue#addTask(Runnable)}.
     * 
     * @param task
     *            The task to run
     * @param timeout
     *            The amount of time that should pass before the task can run.
     * @param unit
     *            The time unit
     * @return A {@link ScheduledTask} to follow the progress of the task execution.
     */
    default ScheduledTask runAfter(Runnable task, long timeout, TimeUnit unit) {
        long runAfter = System.currentTimeMillis() + unit.toMillis(timeout);
        return runAt(task, runAfter);
    }

    /**
     * Schedule a task to be run after the given time. Once the given time has past the task will be added to the task queue,
     * just like a task registered with {@link TaskQueue#addTask(Runnable)}.
     * 
     * @param task
     *            The task to run
     * @param runAtTimeMillis
     *            The first time the task can run given as the difference, measured in milliseconds, between the current time
     *            and midnight, January 1, 1970 UTC.
     * @return A {@link ScheduledTask} to follow the progress of the task execution.
     */
    ScheduledTask runAt(Runnable task, long runAtTimeMillis);

}
