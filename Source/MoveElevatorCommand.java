package Source;
public class MoveElevatorCommand extends Command {
    private final Elevator elevator;
    private final double targetHeight;

    public MoveElevatorCommand(Elevator elevator, double targetHeight) {
        if (targetHeight < 0.0 || targetHeight > 2.0) {
            throw new IllegalArgumentException("Target must be between 0 and 2 meters.");
        }
        this.elevator = elevator;
        this.targetHeight = targetHeight;
    }

    @Override
    public void initialize() {
        // TODO (with Scho): Print the target height.
        System.out.println(targetHeight);
    }

    @Override
    public void execute() {
        // TODO (with Scho): Calculate targetHeight - current height.
        double error = targetHeight - elevator.getHeight();
        if(error > .5)
            elevator.setMotorSpeed(.5);
        else if(error >= .05 && error <= .5)
            elevator.setMotorSpeed(.1);
        else if(error <= -.05 && error >= -.5)
            elevator.setMotorSpeed(-.1);
        else if(error <= -.5){
            elevator.setMotorSpeed(-.5);
        }
        else{
            elevator.stop();
        }
            elevator.update();
        }
        // Set motor speed positive or negative to approach the target.
        // THEN call elevator.update().
        // Hint: Avoid overshooting by reducing speed near the target.
    

    @Override
    public boolean isFinished() {
        // TODO (with Scho): Return true if within 0.05 m of target.
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO (with Scho): Stop the elevator.
        // Print whether the command finished or was interrupted.
    }
}
