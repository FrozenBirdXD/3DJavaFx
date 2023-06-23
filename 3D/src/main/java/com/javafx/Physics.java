package com.javafx;

public class Physics {
    // the smaller the time used for the calculations the more precise and accurate
    // the results (higher update frequency of velocity)
    private static final double GRAVITATIONAL_ACCELERATION = 9.8067;
    private static final double DRAG_COEFFICIENT_FLIGHT = 0.012;
    private static final double AIR_DENSITY = 1.225;
    private static final double WING_AREA = 525.0;
    private static final int MASS = 396893;
    private double velocityX = 0;
    private double velocityZ = 0;
    private double velocity = 0;
    private double angle;

    Physics(double angle) {
        this.angle = angle;
    }

    public double getDeltaX(double time) {
        return time * velocityX;
    }

    public double getDeltaZ(double time) {
        return time * velocityZ;
    }

    public void sleep(double time) {
        velocity -= calcDrag() * time / MASS;
        velocityX = velocity * Math.sin(angle);
        velocityZ = velocity * Math.cos(angle);
    }

    private double calcDrag() {
        return 0.5 * Math.pow(velocity, 2) * DRAG_COEFFICIENT_FLIGHT * WING_AREA;
    }

    public void turn(double angle) {
        this.angle += angle;
        // keep the angle between 0 and 360 degrees (for convenience)
        angle += angle<0?360:angle>360?(-360):0;
    }

    // getter and setter methods
    public double getVelocityZ() {
        return velocityZ;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}