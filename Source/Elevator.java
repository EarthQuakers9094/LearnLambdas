package Source;
// Each update() call represents one simulated step, not elapsed real time.
public class Elevator {
    private final Motor motor = new Motor();
    private double height = 0.0;

    public double getHeight() {
        return height;
    }

    public void setMotorSpeed(double speed) {
        motor.setSpeed(speed);
    }

    public void update() {
        height += motor.getSpeed() * 0.1;
        height = Math.max(0.0, Math.min(2.0, height));
        System.out.printf("Elevator height: %.2f m%n", height);
    }

    public void stop() {
        motor.stop();
    }
}
