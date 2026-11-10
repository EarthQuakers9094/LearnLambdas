package Source;
import java.util.Scanner;

public class ControllerDemo {
    public static void main(String[] args) {
        Motor motor = new Motor();
        Elevator elevator = new Elevator();
        FakeController controller = new FakeController();

        // PART 1 — Simple commands and bindings
        // TODO: Bind A to a RunCommand that sets motor speed to 0.75.
        // TODO: Bind B to a RunCommand that stops the motor (method reference).
        // TODO: Bind X to reverse the motor at -0.5.
        // TODO: Bind Y to a command that prints a robot status message.

        // PART 2 — A custom command class
        Command moveUp = new MoveElevatorCommand(elevator, 1.5);
        // The E key below runs this command with a simple blocking helper.
        // Unlike our one-shot button press, this repeats execute() until finished.

        // PART 3 — Changing values with a supplier
        // TODO: Create a DoubleSupplier named speed using controller::getJoystickY.
        // TODO: Change the placeholder below to a RunCommand that reads
        //       speed.getAsDouble() and uses it to set motor speed.
        Command joystickMotor = new RunCommand(() -> {});

        Scanner scanner = new Scanner(System.in);
        System.out.println("WEEK 8 CONTROLLER SIMULATOR");
        System.out.println("A/B/X/Y = buttons | E = move elevator to 1.5 m");
        System.out.println("S 0.5 = set joystick | G = use joystick speed | Q = quit");

        boolean running = true;
        while (running) {
            System.out.print("Input: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Q")) {
                running = false;
            } else if (input.equals("E")) {
                runToCompletion(moveUp);
            } else if (input.equals("G")) {
                joystickMotor.execute();
            } else if (input.startsWith("S ")) {
                try {
                    double value = Double.parseDouble(input.substring(2));
                    if (!Double.isFinite(value)) {
                        System.out.println("Enter a finite number from -1 to 1.");
                    } else {
                        controller.setJoystickY(value);
                        System.out.println("Joystick Y: " + controller.getJoystickY());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number. Example: S 0.5");
                }
            } else {
                controller.press(input);
            }
        }

        motor.stop();
        elevator.stop();
        scanner.close();
    }

    // Provided simulator helper. This is NOT how the real WPILib scheduler works.
    public static void runToCompletion(Command command) {
        command.initialize();
        int cycles = 0;
        final int MAX_CYCLES = 100;

        while (!command.isFinished() && cycles < MAX_CYCLES) {
            command.execute();
            cycles++;
        }

        boolean interrupted = !command.isFinished();
        command.end(interrupted);
        if (interrupted) {
            System.out.println("Command stopped at simulation cycle limit.");
        }
    }
}
