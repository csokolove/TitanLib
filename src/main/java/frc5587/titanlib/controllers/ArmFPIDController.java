package frc5587.titanlib.controllers;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.math.controller.ArmFeedforward;

public class ArmFPIDController extends PIDController {
    protected double f, p, i, d;
    protected ArmFeedforward armFF;
    private boolean fDisabled = false;

    // params fpid are Feedforward and PID gains, param sCosVA is an array of the ks, kcos, kv, and ka used by an ArmFeedForward
    // all values should come from robot characterization.
    public ArmFPIDController(double f, double p, double i, double d, double[] sCosVA) {
        super(p, i, d);
        this.f = f;
        this.p = p;
        this.i = i;
        this.d = d;
        armFF = new ArmFeedforward(sCosVA[0], sCosVA[1], sCosVA[2], sCosVA[3]);
    }

    // you can also pass an ArmFeedforward instead of an array of the values needed to create one
    public ArmFPIDController(double f, double p, double i, double d, ArmFeedforward armFF) {
        super(p, i, d);
        this.f = f;
        this.p = p;
        this.i = i;
        this.d = d;
        this.armFF = armFF;
    }

    /**
     * @return Returns true if FeedForward is enabled
     */
    public boolean isFEnabled() {
        return !fDisabled;
    }

    /**
     * Disables FeedForward control/integration
     */
    public void disableFeedForward() {
        fDisabled = true;
    }

    /**
     * Enables FeedForward control/integration
     */
    public void enableFeedForward() {
        fDisabled = false;
    }

    /**
     * @return Returns FeedForward gain
    */
    public double getF() {
        return f;
    }

    /**
     * Sets the FeedForward gain
     * @param f - The FeedForward gain
     */
    public void setF(double f) {
        this.f = f;
    }

    /**
     * Sets the Feedfoward and PID gains
     * @param f
     * @param p
     * @param i
     * @param d
     */
    public void setFPID(double f, double p, double i, double d) {
        this.setPID(p, i, d);
        this.f = f;
    }

    /**
     * Calculates the Feedforward value based on position and velocity
     * @param position
     * @param velocity
     * @return
     */
    public double calculateF(double position, double velocity) {
        return armFF.calculate(position, velocity);
    }

    /**
     * @return Returns the ArmFeedforward armFF of this type in case it is needed
     */
    public ArmFeedforward getArmFeedforward() {
        return armFF;
    }

    public double calculate(double measurement, double position, double velocity) {
        // if Feedforward is disabled, only return the result of PIDController's calculate() method
        if(fDisabled) {
            System.out.println("Tried to calculate FPID with Feedforward disabled. Use PIDController.calculate() instead.");
            return super.calculate(measurement);
        }
        else {
            // returns the calculated PID from PIDController multiplied by the calculated Feedforward value.
            return super.calculate(measurement) * calculateF(position, velocity);
        }
    }

    /**
     * Initializes this as a Sendable Object; using all of the f, p, i and d values.
     * This can't just use PIDController's initSendable method, because we need f as well.
     * @param builder - A sendable builder
     */
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PIDController");
        builder.addDoubleProperty("f", this::getF, this::setF);
        builder.addDoubleProperty("p", this::getP, this::setP);
        builder.addDoubleProperty("i", this::getI, this::setI);
        builder.addDoubleProperty("d", this::getD, this::setD);
        builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
    }
}