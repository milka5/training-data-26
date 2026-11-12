/** Координатор пошуку точного значення та значень, менших за заданий поріг. */
public class BasicDataOperations {
    static final String PATH_TO_DATA_FILE = "list/byte.data";
    static final String SORTED_FILE_PREFIX = "list/byte.sorted";

    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            System.err.println("Використання: java BasicDataOperations <значення> <поріг> [файл-даних]");
            System.err.println("Приклад: java BasicDataOperations \"-64\" \"0\"");
            System.exit(1);
            return;
        }
        Byte searched;
        Byte threshold;
        try {
            searched = Byte.valueOf(args[0]);
            threshold = Byte.valueOf(args[1]);
        } catch (NumberFormatException exception) {
            System.err.println("Некоректні аргументи (очікуються числа типу Byte від -128 до 127): " + exception.getMessage());
            System.exit(1);
            return;
        }

        String file = args.length == 3 ? args[2] : PATH_TO_DATA_FILE;
        try {
            Byte[] values = DataFileHandler.loadArrayFromFile(file);
            System.out.println("Точне значення для пошуку: " + searched);
            System.out.println("Завантажено значень: " + values.length);
            System.out.println("LIST");
            new BasicDataOperationsUsingList(searched, values).executeDataOperations(threshold);
            System.out.println("SET");
            new BasicDataOperationsUsingSet(searched, values).executeDataAnalysis();
            System.out.println("QUEUE");
            new BasicDataOperationsUsingQueue(searched, values).runDataProcessing();
        } catch (Exception exception) {
            System.err.println("Помилка обробки даних: " + exception.getMessage());
            System.exit(1);
        }
    }
}