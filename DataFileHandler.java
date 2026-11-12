import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Клас DataFileHandler управляє роботою з файлами даних Byte.
 */
public class DataFileHandler {
    /**
     * Завантажує масив об'єктів Byte з файлу.
     * 
     * @param filePath Шлях до файлу з даними.
     * @return Масив об'єктів Byte.
     */
    public static Byte[] loadArrayFromFile(String filePath) {
        Byte[] temporaryArray = new Byte[1000];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                // Видаляємо можливі невидимі символи та BOM
                currentLine = currentLine.trim().replaceAll("^\\uFEFF", "");
                if (!currentLine.isEmpty()) {
                    Byte parsedValue = Byte.valueOf(currentLine);
                    temporaryArray[currentIndex++] = parsedValue;
                }
            }
        } catch (IOException ioException) {
            throw new java.io.UncheckedIOException("Не вдалося прочитати " + filePath, ioException);
        }

        Byte[] resultArray = new Byte[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Зберігає масив об'єктів Byte у файл.
     * 
     * @param dataArray Масив об'єктів Byte.
     * @param filePath Шлях до файлу для збереження.
     */
    public static void writeArrayToFile(Byte[] dataArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Byte dataElement : dataArray) {
                fileWriter.write(dataElement.toString());
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            throw new java.io.UncheckedIOException("Не вдалося записати " + filePath, ioException);
        }
    }
}