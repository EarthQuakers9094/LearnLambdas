package Source;
public class LambdaDemo {

    public static void main(String[] args) {
        // Once you write Runnable.java and DoubleSupplier.java,
        // uncomment this example and run it.

        /*
        Runnable greeting = () -> {
            System.out.println("Hello!");
            System.out.println("Welcome to robotics!");
        };
        greeting.run();

        DoubleSupplier number = () -> 0.75;
        System.out.println(number.getAsDouble());

        // TODO: Make a DoubleSupplier that returns Math.random() * 100.
        // TODO: Create a Runnable that prints a short countdown.
        // Try a method reference using LambdaDemo::sayHello.
        */
    }

    public static void sayHello() {
        System.out.println("Hello from a method!");
    }
}
