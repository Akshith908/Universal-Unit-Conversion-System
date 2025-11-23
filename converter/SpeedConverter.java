package converter;

public class SpeedConverter extends Converter {

    private double toMetersPerSecond(double v, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "m/s":
            case "mps":
            case "meter/second":
            case "meters/second":
                return v;

            case "km/h":
            case "kph":
            case "kilometer/hour":
            case "kilometers/hour":
                return v * 1000.0 / 3600.0;

            case "mph":
            case "mile/hour":
            case "miles/hour":
                return v * 1609.344 / 3600.0;

            case "ft/s":
            case "fps":
            case "foot/second":
            case "feet/second":
                return v * 0.3048;

            default:
                throw new InvalidUnitException("Unsupported speed unit: " + unit);
        }
    }

    private double fromMetersPerSecond(double ms, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "m/s":
            case "mps":
            case "meter/second":
            case "meters/second":
                return ms;

            case "km/h":
            case "kph":
            case "kilometer/hour":
            case "kilometers/hour":
                return ms * 3600.0 / 1000.0;

            case "mph":
            case "mile/hour":
            case "miles/hour":
                return ms * 3600.0 / 1609.344;

            case "ft/s":
            case "fps":
            case "foot/second":
            case "feet/second":
                return ms / 0.3048;

            default:
                throw new InvalidUnitException("Unsupported speed unit: " + unit);
        }
    }

    @Override
    public double convert(String fromUnit, String toUnit, double value) throws InvalidUnitException {
        return fromMetersPerSecond(toMetersPerSecond(value, fromUnit), toUnit);
    }
}
