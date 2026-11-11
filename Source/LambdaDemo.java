package Source;

import java.util.Random;

public class LambdaDemo {

    public static void main(String[] args) {
        // Once you write Runnable.java and DoubleSupplier.java,
        // uncomment this example and run it.

        
        Runnable greeting = () -> {
            System.out.println("Hello!");
            System.out.println("Welcome to robotics!");
        };
        greeting.run();

        DoubleSupplier number = () -> 0.75;
        System.out.println(number.getAsDouble());

        // TODO: Make a DoubleSupplier that returns Math.random() * 100.
        DoubleSupplier randomNum = () -> {
            Random random = new Random();
            return random.nextDouble(100); 
        };
        // TODO: Create a Runnable that prints a short countdown.
        Runnable countdown = () -> {
            System.out.println("5");
            System.out.println("0");
        };
        // Try a method reference using LambdaDemo::sayHello.

        Runnable helloRunnable = LambdaDemo::sayHello;

        System.out.println(randomNum.getAsDouble());
        countdown.run();
        helloRunnable.run();
        
    }

    public static void sayHello() {
        System.out.println("Hello from a method!");
    }
}
