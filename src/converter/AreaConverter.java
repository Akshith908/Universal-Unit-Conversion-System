package converter;

public class AreaConverter extends Converter {

    @Override
    public double convert(String from, String to, double value) throws InvalidUnitException {
        double inM2 = toSquareMeters(from, value);
        return fromSquareMeters(to, inM2);
    }

    private double toSquareMeters(String unit, double v) throws InvalidUnitException {
        switch (unit) {
            case "m²": return v;
            case "km²": return v * 1_000_000.0;
            case "cm²": return v * 0.0001;
            case "mm²": return v * 0.000001;
            case "in²": return v * 0.00064516;
            case "ft²": return v * 0.09290304;
            case "yd²": return v * 0.83612736;
            case "acre": return v * 4046.8564224;
            case "hectare": return v * 10000.0;
            default: throw new InvalidUnitException("Unknown area unit: " + unit);
        }
    }

    private double fromSquareMeters(String unit, double v) throws InvalidUnitException {
        switch (unit) {
            case "m²": return v;
            case "km²": return v / 1_000_000.0;
            case "cm²": return v / 0.0001;
            case "mm²": return v / 0.000001;
            case "in²": return v / 0.00064516;
            case "ft²": return v / 0.09290304;
            case "yd²": return v / 0.83612736;
            case "acre": return v / 4046.8564224;
            case "hectare": return v / 10000.0;
            default: throw new InvalidUnitException("Unknown area unit: " + unit);
        }
    }
}
