package Source;
public class Motor {
    private double speed = 0.0;

    public void setSpeed(double speed) {
        this.speed = Math.max(-1.0, Math.min(1.0, speed));
        System.out.println("Motor speed: " + this.speed);
    }

    public double getSpeed() {
        return speed;
    }

    public void stop() {
        setSpeed(0.0);
    }
}
