package frc5587.titanlib.pid;

/**
 * FPID
 */
public class FPID extends PID{
    public final double kF;

    public FPID(double kF, double kP, double kI, double kD) {
        super(kP, kI, kD);
        this.kF = kF;
    }
}