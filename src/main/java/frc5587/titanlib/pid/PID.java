package frc5587.titanlib.pid;

/**
 * PID
 */
public class PID {
    public final double kP;
    public final double kI;
    public final double kD;

    public PID(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
}