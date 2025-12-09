package converter;

public class PowerConverter extends Converter {

    @Override
    public double convert(String from, String to, double value) throws InvalidUnitException {
        double inWatt = toWatt(from, value);
        return fromWatt(to, inWatt);
    }

    private double toWatt(String u, double v) throws InvalidUnitException {
        switch (u) {
            case "W": return v;
            case "kW": return v * 1000.0;
            case "MW": return v * 1_000_000.0;
            case "HP": return v * 745.699872;
            case "BTU/h": return v * 0.29307107;
            default: throw new InvalidUnitException("Unknown power unit: " + u);
        }
    }

    private double fromWatt(String u, double v) throws InvalidUnitException {
        switch (u) {
            case "W": return v;
            case "kW": return v / 1000.0;
            case "MW": return v / 1_000_000.0;
            case "HP": return v / 745.699872;
            case "BTU/h": return v / 0.29307107;
            default: throw new InvalidUnitException("Unknown power unit: " + u);
        }
    }
}
