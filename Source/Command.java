package Source;
// Simplified version of the WPILib Command lifecycle.
public abstract class Command {
    public void initialize() {}
    public void execute() {}
    public boolean isFinished() { return false; }
    public void end(boolean interrupted) {}
}
