package converter;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends Converter {

    private static final Map<String, Double> toUSD = new HashMap<>();

    static {
        toUSD.put("USD", 1.0);
        toUSD.put("EUR", 1.08);
        toUSD.put("GBP", 1.25);
        toUSD.put("INR", 0.012);
        toUSD.put("JPY", 0.0068);
        toUSD.put("CAD", 0.74);
        toUSD.put("AUD", 0.67);
    }

    @Override
    public double convert(String from, String to, double value) throws InvalidUnitException {
        Double f = toUSD.get(from);
        Double t = toUSD.get(to);

        if (f == null) throw new InvalidUnitException("Unknown currency: " + from);
        if (t == null) throw new InvalidUnitException("Unknown currency: " + to);

        return (value * f) / t;
    }
}
