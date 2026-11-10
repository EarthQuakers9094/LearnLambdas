package Source;
import java.util.HashMap;

public class FakeController {
    private final HashMap<String, Command> bindings = new HashMap<>();
    private double joystickY = 0.0;

    public void bind(String button, Command command) {
        // TODO: Store a button/command pair in bindings.
        // Tip: Keep button names uppercase to match keyboard input.
    }

    public void press(String button) {
        // TODO: Look up the Command in bindings.
        // If present, call initialize(), execute(), then end(false).
        // Otherwise print an error message.
        // This is one simulated press, NOT a full scheduler.
    }

    public void setJoystickY(double value) {
        joystickY = Math.max(-1.0, Math.min(1.0, value));
    }

    public double getJoystickY() {
        return joystickY;
    }
}
