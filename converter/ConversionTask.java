package converter;

public class ConversionTask implements Runnable {
    private final Converter converter;
    private final String from, to, category;
    private final double value;
    private final ConversionHistory history;

    public ConversionTask(String category, Converter converter, String from, String to, double value, ConversionHistory history) {
        this.category = category;
        this.converter = converter;
        this.from = from;
        this.to = to;
        this.value = value;
        this.history = history;
    }

    @Override
    public void run() {
        try {
            double result = converter.convert(from, to, value);
            String record = history.formatRecord(category, from, to, value, result);
            history.save(record);
            System.out.printf("[Thread %s] %s: %.6f %s -> %.6f %s%n",
                    Thread.currentThread().getName(), category, value, from, result, to);
        } catch (InvalidUnitException e) {
            System.err.println("Conversion error: " + e.getMessage());
        }
    }
}
