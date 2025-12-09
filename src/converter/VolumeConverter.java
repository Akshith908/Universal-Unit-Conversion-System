package converter;

public class VolumeConverter extends Converter {

    private double toLiters(double v, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "l":
            case "liter":
            case "liters":
                return v;

            case "ml":
            case "milliliter":
            case "milliliters":
                return v / 1000.0;

            case "m3":
            case "m^3":
            case "cubic meter":
            case "cubic meters":
                return v * 1000.0;

            case "cm3":
            case "cm^3":
            case "cubic centimeter":
            case "cubic centimeters":
            case "cc":
                return v / 1000.0;

            case "gal":
            case "gallon":
            case "gallons":
                return v * 3.785411784; // US gallon

            case "pt":
            case "pint":
            case "pints":
                return v * 0.473176473; // US pint

            default:
                throw new InvalidUnitException("Unsupported volume unit: " + unit);
        }
    }

    private double fromLiters(double l, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "l":
            case "liter":
            case "liters":
                return l;

            case "ml":
            case "milliliter":
            case "milliliters":
                return l * 1000.0;

            case "m3":
            case "m^3":
            case "cubic meter":
            case "cubic meters":
                return l / 1000.0;

            case "cm3":
            case "cm^3":
            case "cubic centimeter":
            case "cubic centimeters":
            case "cc":
                return l * 1000.0;

            case "gal":
            case "gallon":
            case "gallons":
                return l / 3.785411784;

            case "pt":
            case "pint":
            case "pints":
                return l / 0.473176473;

            default:
                throw new InvalidUnitException("Unsupported volume unit: " + unit);
        }
    }

    @Override
    public double convert(String fromUnit, String toUnit, double value) throws InvalidUnitException {
        return fromLiters(toLiters(value, fromUnit), toUnit);
    }
}
