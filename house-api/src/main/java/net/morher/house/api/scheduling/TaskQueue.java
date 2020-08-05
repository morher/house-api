package net.morher.house.api.scheduling;

/**
 * The task queue holds a list of tasks to be run in the future or to be off-loaded to another thread. What thread runs the task
 * and what the requirements for executing the task are implementation details.
 * 
 * @author Morten Hermansen
 */
public interface TaskQueue {

    /**
     * Register a task to be run. The task can be canceled at any time before execution has started.
     * 
     * @param task
     *            The task to run
     * @return A {@link ScheduledTask} to follow the progress of the task execution.
     */
    ScheduledTask addTask(Runnable task);
}
