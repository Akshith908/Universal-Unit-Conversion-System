package converter;

public class PressureConverter extends Converter {

    @Override
    public double convert(String from, String to, double value) throws InvalidUnitException {
        double inPa = toPascal(from, value);
        return fromPascal(to, inPa);
    }

    private double toPascal(String u, double v) throws InvalidUnitException {
        switch (u) {
            case "Pa": return v;
            case "kPa": return v * 1000.0;
            case "MPa": return v * 1e6;
            case "bar": return v * 100000.0;
            case "psi": return v * 6894.757293168;
            case "atm": return v * 101325.0;
            case "mmHg": return v * 133.322387415;
            case "inHg": return v * 3386.389;
            default: throw new InvalidUnitException("Unknown pressure unit: " + u);
        }
    }

    private double fromPascal(String u, double v) throws InvalidUnitException {
        switch (u) {
            case "Pa": return v;
            case "kPa": return v / 1000.0;
            case "MPa": return v / 1e6;
            case "bar": return v / 100000.0;
            case "psi": return v / 6894.757293168;
            case "atm": return v / 101325.0;
            case "mmHg": return v / 133.322387415;
            case "inHg": return v / 3386.389;
            default: throw new InvalidUnitException("Unknown pressure unit: " + u);
        }
    }
}
