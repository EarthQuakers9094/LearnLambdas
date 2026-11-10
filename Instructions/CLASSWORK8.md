# Classwork — Lambdas, Functional Interfaces, and Commands

**Goal:** Learn how to pass actions and changing values between objects using functional interfaces and lambdas. Build a simplified FRC-style command framework and use it to control a motor and elevator.

Last week, we worked with inheritance, abstract classes, and interfaces. We'll build on those ideas here. Complete the guided sections with Scho and the remaining sections independently.

## 0. A problem to think about

Imagine programming a robot whose joystick controls a motor. The joystick changes as the driver moves it, so we need its **current value** whenever we update the motor.

Consider this pseudocode (we haven't written this `Controller` class):

```java
Controller controller = new Controller();
controller.setJoystick(0.5);
double speed = controller.getJoystick();
controller.setJoystick(0.8);
System.out.println(speed);
```

**Discuss:** What prints? Why doesn't `speed` automatically update? How could we give another object a way to retrieve the *current* value whenever it needs it?

Normally, assigning a variable stores a value at that moment, and calling a method executes its code immediately. But what if we want to **store instructions for how to retrieve a value or execute code later**?

That's what lambdas help us do. We'll use two functional interfaces:

- `Runnable` represents an action we can execute later.
- `DoubleSupplier` represents a way to get a `double` whenever we ask for it.

## 1. Two functional interfaces

A **functional interface** has exactly one abstract method. Because there is only one, Java can use a lambda to supply that method's implementation.

### With Scho: Runnable

Open `Runnable.java`. Write:

```java
@FunctionalInterface
public interface Runnable {
    void run();
}
```

The annotation asks Java to verify that this is a functional interface. Temporarily add a second abstract method. What error do you get? Remove it again.

### Independent: DoubleSupplier

In `DoubleSupplier.java`, write a functional interface with one method: `double getAsDouble()`. It takes no parameters. Use `@FunctionalInterface`.

**Note:** Java already has built-in versions of `Runnable` and `DoubleSupplier`. We're writing teaching copies to understand how they work. They're different types from the built-in versions. We'll delete these copies and use Java's interfaces when we begin WPILib.

## 2. Lambdas and method references

Open `LambdaDemo.java` and uncomment the example after you've written both interfaces.

```java
Runnable greeting = () -> {
    System.out.println("Hello!");
};

greeting.run();
```

`()` is the parameter list for `run()` (empty here), and `->` introduces its implementation. Creating the lambda does **not** run the code; `greeting.run()` does.

A supplier works similarly, but it returns a number:

```java
DoubleSupplier number = () -> 0.75;
System.out.println(number.getAsDouble());
```

If we just need to store `0.75`, a regular `double` is simpler. The point of a supplier is that it can calculate or retrieve a value *later*, potentially returning a different value each time.

**Try it:** Change `number` so it returns `Math.random() * 100`. Call `getAsDouble()` a few times. Create a `Runnable` that prints a short countdown.

When a lambda only calls an existing method, a **method reference** can be shorter:

```java
Runnable first = () -> LambdaDemo.sayHello();
Runnable second = LambdaDemo::sayHello;
```

Try both. They reference the same action. In a multi-line lambda that returns a value, remember to write an explicit `return` statement.

## 3. Commands: simple actions versus complex behavior

Open `Command.java`. This is our simplified version of WPILib's abstract command class.

| Method | Purpose |
| --- | --- |
| `initialize()` | Runs when the command starts |
| `execute()` | Runs while the command is active |
| `isFinished()` | Says whether the command should finish |
| `end(boolean interrupted)` | Runs when it ends |

We could create a separate command class for every motor action. We often **do** create custom classes in FRC, especially for complex behaviors that need state, several lifecycle methods, or a completion condition. But for straightforward actions, a reusable class that accepts a `Runnable` is convenient.

### With Scho: Build RunCommand

In `RunCommand.java`:

1. Add a private `Runnable` field.
2. Store the constructor's `Runnable` in that field.
3. Override `execute()` to call the stored action's `run()` method.

`RunCommand` **is a** `Command` (inheritance) and **has a** `Runnable` (composition).

### Test with a motor

Examine the provided `Motor.java`. In `ControllerDemo.java`, create these commands to try before adding button bindings:

```java
Command spinMotor = new RunCommand(() -> motor.setSpeed(0.75));
Command stopMotor = new RunCommand(motor::stop);

spinMotor.execute();
stopMotor.execute();
```

A `RunCommand` executes the action you gave it; you don't need to write a new `SpinMotorCommand` class for this simple behavior. Note: Our simulator calls `execute()` manually; a real WPILib `RunCommand` is scheduled repeatedly while active.

**Discuss:** What would be gained by writing a separate class for a more complicated robot behavior?

## 4. With Scho: A custom elevator command

Open `Elevator.java`. It **has a** `Motor` and stores its current height. Calling `setMotorSpeed()` changes the motor speed; each call to `update()` then advances the elevator by one simulated step. There are no threads or real-time physics here.

Now imagine a command that moves the elevator automatically to **1.5 meters**. It must remember a target height, move toward it, decide when it has arrived, and stop its motor. This is a good use for a **custom `Command` subclass**.

Open `MoveElevatorCommand.java`. Its fields and constructor are provided. With Scho, implement the lifecycle methods:

1. `initialize()`: Print the target height.
2. `execute()`: Calculate `double error = targetHeight - elevator.getHeight();`. Set the motor speed based on the sign of the error, then call `elevator.update()`. As you get close, reduce motor speed to avoid moving back and forth over the target.
3. `isFinished()`: Return whether `Math.abs(targetHeight - elevator.getHeight()) < 0.05`.
4. `end(boolean interrupted)`: Stop the motor and print whether it finished or was interrupted.

**Test:** In `ControllerDemo`, press **E** to run the provided `MoveElevatorCommand` to 1.5 m. The provided `runToCompletion()` helper calls `execute()` repeatedly until `isFinished()` returns true, with a 100-cycle safety limit. If it reaches the cycle limit, inspect your code.

This helper **blocks** while it runs. It is only a classroom simulation, not WPILib's real command scheduler.

**Discuss:** Why does the elevator command need its own fields and lifecycle logic, while the simple motor command can be a `RunCommand`? These are design choices, not hard rules: either style can be useful depending on the behavior.

## 5. Controller bindings

Open `FakeController.java`. Its `HashMap<String, Command>` stores buttons as keys and commands as values.

**With Scho:** Implement `bind(String button, Command command)` to store the pair in the map.

**Independently:** Implement `press(String button)` to look up the command. If found, call `initialize()`, `execute()` once, and `end(false)`. If not found, print a helpful message. This is a **one-shot button demonstration**, not a real scheduler; the elevator command uses the separate helper.

In `ControllerDemo`, add bindings:

| Button | Action |
| --- | --- |
| A | Set motor speed to 0.75 |
| B | Stop the motor (use a method reference) |
| X | Set motor speed to -0.5 |
| Y | Print a status message using a multi-line lambda |

Test all four buttons. How does `FakeController` execute commands without knowing anything about motors?

## 6. Return to the joystick problem: DoubleSupplier

Our `FakeController` has a field called `joystickY`. The keyboard command `S 0.5` updates it, and `S -0.8` changes it again.

With Scho, compare:

```java
controller.setJoystickY(0.5);
double fixedSpeed = controller.getJoystickY();
DoubleSupplier liveSpeed = controller::getJoystickY;

controller.setJoystickY(-0.8);
System.out.println(fixedSpeed);
System.out.println(liveSpeed.getAsDouble());
```

The first variable keeps the earlier number. The supplier retains **a way to read the current value** each time it's called.

**Independently:** Replace the `joystickMotor` placeholder in `ControllerDemo` with a `RunCommand` that calls `motor.setSpeed(speed.getAsDouble())`, where `speed` is a `DoubleSupplier` you created using `controller::getJoystickY`.

Test the input sequence:

```text
S 0.5
G
S -0.8
G
S 0
G
```

**Explain:** Why doesn't the command need to be recreated after the joystick value changes?

## Check your understanding

1. Why would you use a lambda instead of calling a method immediately?
2. What makes `Runnable` and `DoubleSupplier` functional interfaces?
3. What's the difference between `RunCommand` and a custom command class?
4. How do `initialize()`, `execute()`, `isFinished()`, and `end()` work together?
5. Why do we pass a `DoubleSupplier` instead of a fixed `double` when values change?

**After class:** Finish anything incomplete and continue with `HOMEWORK8.md`.
