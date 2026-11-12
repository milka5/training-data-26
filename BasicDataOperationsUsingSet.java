import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Клас BasicDataOperationsUsingSet реалізує операції з множиною TreeSet для Byte.
 */
public class BasicDataOperationsUsingSet {
    Byte valueToSearch;
    int sourceValueCount;
    Set<Byte> dataSet = new TreeSet<>();

    BasicDataOperationsUsingSet(Byte valueToSearch, Byte[] dataArray) {
        this.valueToSearch = valueToSearch;
        this.sourceValueCount = dataArray.length;
        this.dataSet = new TreeSet<>(Arrays.asList(dataArray));
    }

    public void executeDataAnalysis() {
        findInSet();
        locateMinMaxInSet();
        analyzeDuplicates();

        Byte[] sortedSetValues = dataSet.toArray(new Byte[0]);
        Arrays.sort(sortedSetValues);
        DataFileHandler.writeArrayToFile(sortedSetValues, BasicDataOperations.SORTED_FILE_PREFIX + ".set.data");
    }

    private void findInSet() {
        String operationName = "пошук елемента '" + valueToSearch + "' в TreeSet<Byte>";
        long timeStart = System.nanoTime();

        boolean elementExists = this.dataSet.contains(valueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (elementExists) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в TreeSet");
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в TreeSet.");
        }
    }

    private void locateMinMaxInSet() {
        if (dataSet == null || dataSet.isEmpty()) {
            System.out.println("TreeSet є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Byte minValue = Collections.min(dataSet);
        Byte maxValue = Collections.max(dataSet);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в TreeSet<Byte>");

        System.out.println("Найменше значення в TreeSet: " + minValue);
        System.out.println("Найбільше значення в TreeSet: " + maxValue);
    }

    private void analyzeDuplicates() {
        System.out.println("Кiлькiсть вхiдних значень: " + sourceValueCount);
        System.out.println("Кiлькiсть елементiв в TreeSet: " + dataSet.size());
        System.out.println("Повторних входжень, вiдкинутих TreeSet: " + (sourceValueCount - dataSet.size()));
    }
}