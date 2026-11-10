# Homework — Extend the Command Simulator

**Target:** About 1–2 hours, including any unfinished classwork. This project stays separate from your Week 7 robot simulator; we'll combine them in Week 9.

## Part 1 — Test the elevator command

Make sure `MoveElevatorCommand` works for a target of 1.5 m. Then change or create commands to test targets of **0.5 m**, **2.0 m**, and **0.0 m**. The elevator should move in the correct direction and stop near each target. Remember: the same `Elevator` object keeps its position between commands.

Test what happens if the command starts when the elevator is *already at its target*. Confirm that it finishes without unnecessarily moving.

**Explain:** Which lifecycle method determines when the command should stop? Which one actually stops its motor?

## Part 2 — A custom command

Create `MoveElevatorByCommand.java`, extending `Command`. Instead of receiving an absolute target height, its constructor should receive an `Elevator` and a *change in height* (e.g. `+0.5 m` or `-0.5 m`).

Requirements:

1. Store the elevator and the requested height change in fields.
2. In `initialize()`, determine and store the target using the elevator's **current** height. Keep the target between 0.0 and 2.0 m.
3. In `execute()`, move toward that target using the same approach as `MoveElevatorCommand`.
4. In `isFinished()`, return true when the elevator is within 0.05 m of the target.
5. In `end(boolean interrupted)`, stop the motor.

Test the **same command object twice** using `runToCompletion()`. Does each execution move the elevator by the requested amount from its current position? Why is `initialize()` the right place to calculate the target?

## Part 3 — Suppliers and motor speed

Create a second `DoubleSupplier` called `slowSpeed` that returns half the **current** joystick value. Use it in a new `RunCommand` that sets the motor speed.

Add a keyboard option for the slow command (you can add a new case in `ControllerDemo`). Test joystick values `1.0`, `0.6`, `-0.8`, and `0.0` and confirm the motor receives half those values.

Then answer: Why would this be less useful if you calculated a regular `double slowSpeed` just once when the program started?

## Part 4 — Test and reflect

Make sure the following work:

| Test | Expected result |
| --- | --- |
| Press A, then B | Motor runs at 0.75, then stops |
| Press X | Motor runs at -0.5 |
| Press an unused button | Helpful message, no crash |
| E: move elevator | Elevator approaches 1.5 m and finishes |
| Move elevator to 0.0 m | Elevator travels downward and stops |
| Set joystick value, then G | Motor uses current joystick speed |
| Change joystick, then G again | Same command uses the new value |
| Slow-speed command | Motor uses half the current joystick value |

Briefly answer in comments or a separate file:

1. How are an abstract `Command` class and a functional `Runnable` interface different?
2. Why does `RunCommand` **contain** a `Runnable` rather than implement it?
3. When would you write a custom command class instead of using `RunCommand`?
4. What changes when a lambda retrieves a value through a `DoubleSupplier` instead of storing a fixed number?

## Optional challenge — Button-driven elevator

Our `FakeController.press()` currently executes a command **once**, so it can't automatically complete an elevator movement. Add a separate `pressToCompletion()` method or a new keyboard option that looks up a bound elevator command and passes it to `runToCompletion()`. Don't change the one-shot behavior of ordinary button presses.

**Discuss:** Why is this still different from a real WPILib command scheduler?

## Turn in

Push the finished Week 8 project to your personal GitHub repository. Make sure `LambdaDemo` and `ControllerDemo` compile and that you can explain the code you wrote.
