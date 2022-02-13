package frc5587.titanlib.control;

/**
 * Cycle
 */
public interface Cycle<T> {
    public T reset();
    public T prev();
    public T next();
    public T current();
    public T getValueForStep(int step);
}