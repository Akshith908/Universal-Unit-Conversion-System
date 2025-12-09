package converter;

public class DataConverter extends Converter {

    @Override
    public double convert(String from, String to, double value) throws InvalidUnitException {
        double bytes = toBytes(from, value);
        return fromBytes(to, bytes);
    }

    private double toBytes(String u, double v) throws InvalidUnitException {
        switch (u.toLowerCase()) {
            case "bit": return v / 8.0;
            case "byte": return v;
            case "kb": return v * 1_000.0;
            case "mb": return v * 1_000_000.0;
            case "gb": return v * 1_000_000_000.0;
            case "tb": return v * 1_000_000_000_000.0;
            case "pb": return v * 1_000_000_000_000_000.0;
            default: throw new InvalidUnitException("Unknown data unit: " + u);
        }
    }

    private double fromBytes(String u, double v) throws InvalidUnitException {
        switch (u.toLowerCase()) {
            case "bit": return v * 8.0;
            case "byte": return v;
            case "kb": return v / 1_000.0;
            case "mb": return v / 1_000_000.0;
            case "gb": return v / 1_000_000_000.0;
            case "tb": return v / 1_000_000_000_000.0;
            case "pb": return v / 1_000_000_000_000_000.0;
            default: throw new InvalidUnitException("Unknown data unit: " + u);
        }
    }
}
