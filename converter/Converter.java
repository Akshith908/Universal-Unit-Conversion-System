package converter;

public abstract class Converter {
    public abstract double convert(String fromUnit, String toUnit, double value) throws InvalidUnitException;
}