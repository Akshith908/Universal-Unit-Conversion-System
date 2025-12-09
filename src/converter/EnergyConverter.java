package converter;

public class EnergyConverter extends Converter {

    @Override
    public double convert(String from, String to, double value) throws InvalidUnitException {
        double inJ = toJoules(from, value);
        return fromJoules(to, inJ);
    }

    private double toJoules(String u, double v) throws InvalidUnitException {
        switch (u) {
            case "J": return v;
            case "kJ": return v * 1e3;
            case "MJ": return v * 1e6;
            case "Wh": return v * 3600.0;
            case "kWh": return v * 3.6e6;
            case "cal": return v * 4.184;
            case "kcal": return v * 4184.0;
            case "BTU": return v * 1055.05585;
            default: throw new InvalidUnitException("Unknown energy unit: " + u);
        }
    }

    private double fromJoules(String u, double v) throws InvalidUnitException {
        switch (u) {
            case "J": return v;
            case "kJ": return v / 1e3;
            case "MJ": return v / 1e6;
            case "Wh": return v / 3600.0;
            case "kWh": return v / 3.6e6;
            case "cal": return v / 4.184;
            case "kcal": return v / 4184.0;
            case "BTU": return v / 1055.05585;
            default: throw new InvalidUnitException("Unknown energy unit: " + u);
        }
    }
}
