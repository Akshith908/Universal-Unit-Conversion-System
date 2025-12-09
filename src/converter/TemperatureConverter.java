package converter;

public class TemperatureConverter extends Converter {
    @Override
    public double convert(String fromUnit, String toUnit, double value) throws InvalidUnitException {
        String f = fromUnit.toLowerCase(), t = toUnit.toLowerCase();
        double celsius;
        if (f.equals("c") || f.equals("celsius")) celsius = value;
        else if (f.equals("f") || f.equals("fahrenheit")) celsius = (value - 32.0) * 5.0 / 9.0;
        else if (f.equals("k") || f.equals("kelvin")) celsius = value - 273.15;
        else throw new InvalidUnitException("Unsupported temperature unit: " + fromUnit);

        if (t.equals("c") || t.equals("celsius")) return celsius;
        else if (t.equals("f") || t.equals("fahrenheit")) return celsius * 9.0 / 5.0 + 32.0;
        else if (t.equals("k") || t.equals("kelvin")) return celsius + 273.15;
        else throw new InvalidUnitException("Unsupported temperature unit: " + toUnit);
    }
}
