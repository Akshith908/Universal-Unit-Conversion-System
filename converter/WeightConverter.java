package converter;

public class WeightConverter extends Converter {
    private double toKg(double v, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "kg":
                return v;
            case "g":
                return v / 1000.0;
            case "mg":
                return v / 1_000_000.0;
            case "lb":
                return v * 0.45359237;
            case "oz":
                return v * 0.028349523125;
            case "t":
            case "tonne":
            case "tonnes":
                return v * 1000.0;   // metric tonne
            default:
                throw new InvalidUnitException("Unsupported weight unit: " + unit);
        }
    }

    private double fromKg(double kg, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "kg":
                return kg;
            case "g":
                return kg * 1000.0;
            case "mg":
                return kg * 1_000_000.0;
            case "lb":
                return kg / 0.45359237;
            case "oz":
                return kg / 0.028349523125;
            case "t":
            case "tonne":
            case "tonnes":
                return kg / 1000.0;
            default:
                throw new InvalidUnitException("Unsupported weight unit: " + unit);
        }
    }

    @Override
    public double convert(String fromUnit, String toUnit, double value) throws InvalidUnitException {
        return fromKg(toKg(value, fromUnit), toUnit);
    }
}
