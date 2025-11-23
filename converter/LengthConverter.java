package converter;

public class LengthConverter extends Converter {
    private double toMeters(double value, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "m": case "meter": case "meters":
                return value;
            case "km": case "kilometer": case "kilometers":
                return value * 1000.0;
            case "cm": case "centimeter": case "centimeters":
                return value / 100.0;
            case "mm": case "millimeter": case "millimeters":
                return value / 1000.0;
            case "in": case "inch": case "inches":
                return value * 0.0254;
            case "ft": case "foot": case "feet":
                return value * 0.3048;
            case "yd": case "yard": case "yards":
                return value * 0.9144;
            case "mi": case "mile": case "miles":
                return value * 1609.344;
            default:
                throw new InvalidUnitException("Unsupported length unit: " + unit);
        }
    }

    private double fromMeters(double meters, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "m": case "meter": case "meters":
                return meters;
            case "km": case "kilometer": case "kilometers":
                return meters / 1000.0;
            case "cm": case "centimeter": case "centimeters":
                return meters * 100.0;
            case "mm": case "millimeter": case "millimeters":
                return meters * 1000.0;
            case "in": case "inch": case "inches":
                return meters / 0.0254;
            case "ft": case "foot": case "feet":
                return meters / 0.3048;
            case "yd": case "yard": case "yards":
                return meters / 0.9144;
            case "mi": case "mile": case "miles":
                return meters / 1609.344;
            default:
                throw new InvalidUnitException("Unsupported length unit: " + unit);
        }
    }

    @Override
    public double convert(String fromUnit, String toUnit, double value) throws InvalidUnitException {
        return fromMeters(toMeters(value, fromUnit), toUnit);
    }
}
