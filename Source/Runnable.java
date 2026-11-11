package Source;
// Java already has java.lang.Runnable. We are writing our own version
// to see how functional interfaces work. It is a separate Java type.
// We'll remove this file when we move to WPILib.

// TODO: Declare a @FunctionalInterface named Runnable.
// It must have one abstract method: void run().
@FunctionalInterface 
public interface Runnable {

    public void run();
}