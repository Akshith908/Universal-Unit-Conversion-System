package converter;

public class TimeConverter extends Converter {
    private double toSeconds(double v, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "ms":
            case "millisecond":
            case "milliseconds":
                return v / 1000.0;
            case "s":
            case "sec":
            case "second":
            case "seconds":
                return v;
            case "m":
            case "min":
            case "minute":
            case "minutes":
                return v * 60.0;
            case "h":
            case "hr":
            case "hour":
            case "hours":
                return v * 3600.0;
            case "d":
            case "day":
            case "days":
                return v * 86400.0;
            case "wk":
            case "week":
            case "weeks":
                return v * 7 * 86400.0; // 604800
            default:
                throw new InvalidUnitException("Unsupported time unit: " + unit);
        }
    }

    private double fromSeconds(double s, String unit) throws InvalidUnitException {
        switch (unit.toLowerCase()) {
            case "ms":
            case "millisecond":
            case "milliseconds":
                return s * 1000.0;
            case "s":
            case "sec":
            case "second":
            case "seconds":
                return s;
            case "m":
            case "min":
            case "minute":
            case "minutes":
                return s / 60.0;
            case "h":
            case "hr":
            case "hour":
            case "hours":
                return s / 3600.0;
            case "d":
            case "day":
            case "days":
                return s / 86400.0;
            case "wk":
            case "week":
            case "weeks":
                return s / (7 * 86400.0); // 604800
            default:
                throw new InvalidUnitException("Unsupported time unit: " + unit);
        }
    }

    @Override
    public double convert(String fromUnit, String toUnit, double value) throws InvalidUnitException {
        return fromSeconds(toSeconds(value, fromUnit), toUnit);
    }
}
