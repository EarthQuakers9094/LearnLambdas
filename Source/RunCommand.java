package Source;
public class RunCommand extends Command {

    // TODO: Declare a private field that stores a Runnable.
    private Runnable action; 

    public RunCommand(Runnable action) {
        // TODO: Save the supplied action in your field.
        this.action = action; 
    }

    @Override
    public void execute() {
        // TODO: Run the stored action.
        action.run();
    }
}
