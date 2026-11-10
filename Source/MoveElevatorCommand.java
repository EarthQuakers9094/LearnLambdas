package Source;
public class MoveElevatorCommand extends Command {
    private final Elevator elevator;
    private final double targetHeight;

    public MoveElevatorCommand(Elevator elevator, double targetHeight) {
        if (!Double.isFinite(targetHeight) || targetHeight < 0.0 || targetHeight > 2.0) {
            throw new IllegalArgumentException("Target must be between 0 and 2 meters.");
        }
        this.elevator = elevator;
        this.targetHeight = targetHeight;
    }

    @Override
    public void initialize() {
        // TODO (with Scho): Print the target height.
    }

    @Override
    public void execute() {
        // TODO (with Scho): Calculate targetHeight - current height.
        // Set motor speed positive or negative to approach the target.
        // THEN call elevator.update().
        // Hint: Avoid overshooting by reducing speed near the target.
    }

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
