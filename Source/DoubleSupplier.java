package Source;
// Java already has java.util.function.DoubleSupplier.
// This is our teaching version, not the built-in interface.

// TODO: Declare a @FunctionalInterface named DoubleSupplier.
// It must have one abstract method: double getAsDouble().

@FunctionalInterface 
public interface DoubleSupplier {

    public double getAsDouble();
}
