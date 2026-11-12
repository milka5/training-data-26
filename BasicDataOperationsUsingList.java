import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Клас BasicDataOperationsUsingList реалізує операції з колекціями типу LinkedList для даних Byte.
 */
public class BasicDataOperationsUsingList {
    private boolean listSorted = false;
    private boolean arraySorted = false;
    private Byte valueToSearch;
    private Byte[] dataArray;
    private List<Byte> dataList;

    BasicDataOperationsUsingList(Byte valueToSearch, Byte[] dataArray) {
        this.valueToSearch = valueToSearch;
        this.dataArray = dataArray.clone();
        this.dataList = new LinkedList<>(Arrays.asList(dataArray));
    }

    public void executeDataOperations(Byte threshold) {
        System.out.println("List: до сортування");
        analyzeCondition(dataList, listSorted, threshold);
        findInList();
        locateMinMaxInList();

        sortList();
        System.out.println("List: після сортування");
        analyzeCondition(dataList, listSorted, threshold);

        findInList();
        locateMinMaxInList();

        System.out.println("Масив: до сортування");
        analyzeCondition(dataArray, arraySorted, threshold);
        findInArray();
        locateMinMaxInArray();

        performArraySorting();
        System.out.println("Масив: після сортування");
        analyzeCondition(dataArray, arraySorted, threshold);

        findInArray();
        locateMinMaxInArray();

        DataFileHandler.writeArrayToFile(dataList.toArray(new Byte[0]), BasicDataOperations.SORTED_FILE_PREFIX + ".list.data");
    }

    void performArraySorting() {
        long timeStart = System.nanoTime();
        Arrays.sort(dataArray);
        arraySorted = true;
        PerformanceTracker.displayOperationTime(timeStart, "сортування масиву Byte[]");
    }

    void findInArray() {
        String operationName = "пошук елемента '" + valueToSearch + "' в масивi Byte[], " + (arraySorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        int position = -1;
        if (arraySorted) {
            position = Arrays.binarySearch(dataArray, valueToSearch);
        } else {
            for (int i = 0; i < dataArray.length; i++) {
                if (dataArray[i].compareTo(valueToSearch) == 0) {
                    position = i;
                    break;
                }
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (position >= 0) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в масиві.");
        }
    }

    void locateMinMaxInArray() {
        if (dataArray == null || dataArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        String operationName = "визначення мiнiмального i максимального значення в масивi Byte[], " + (arraySorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        Byte minValue = dataArray[0];
        Byte maxValue = dataArray[0];

        if (arraySorted) {
            maxValue = dataArray[dataArray.length - 1];
        } else {
            for (Byte currentValue : dataArray) {
                if (currentValue.compareTo(minValue) < 0) {
                    minValue = currentValue;
                }
                if (currentValue.compareTo(maxValue) > 0) {
                    maxValue = currentValue;
                }
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);
        System.out.println("Найменше значення в масивi: " + minValue);
        System.out.println("Найбільше значення в масивi: " + maxValue);
    }

    void findInList() {
        String operationName = "пошук елемента '" + valueToSearch + "' в LinkedList<Byte>, " + (listSorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        int position = -1;
        if (listSorted) {
            position = Collections.binarySearch(dataList, valueToSearch);
        } else {
            int index = 0;
            for (Byte value : dataList) {
                if (value.compareTo(valueToSearch) == 0) {
                    position = index;
                    break;
                }
                index++;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (position >= 0) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в LinkedList за позицією: " + position);
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в LinkedList.");
        }
    }

    void locateMinMaxInList() {
        if (dataList == null || dataList.isEmpty()) {
            System.out.println("Колекція LinkedList є пустою або не ініціалізованою.");
            return;
        }

        String operationName = "визначення мiнiмального i максимального значення в LinkedList<Byte>, " + (listSorted ? "після сортування" : "до сортування");
        long timeStart = System.nanoTime();

        Byte minValue;
        Byte maxValue;

        if (listSorted) {
            minValue = dataList.get(0);
            maxValue = dataList.get(dataList.size() - 1);
        } else {
            minValue = Collections.min(dataList);
            maxValue = Collections.max(dataList);
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);
        System.out.println("Найменше значення в List: " + minValue);
        System.out.println("Найбільше значення в List: " + maxValue);
    }

    void sortList() {
        long timeStart = System.nanoTime();
        Collections.sort(dataList);
        listSorted = true;
        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedList<Byte>");
    }

    // BEGIN CONDITION
    public int analyzeCondition(Byte[] values, boolean sorted, Byte threshold) {
        String conditionDescription = "значення менше " + threshold;
        String phase = sorted ? "після сортування" : "до сортування";
        System.out.println("Масив: вибірка даних " + phase + ". Умова: " + conditionDescription);

        String operationName = "вибірка даних: масив Byte[], " + phase + ", умова: " + conditionDescription;
        long timeStart = System.nanoTime();
        Byte[] matches = new Byte[values.length];
        int count = 0;
        int inspected = 0;

        if (!sorted) {
            for (Byte value : values) {
                inspected++;
                if (value.compareTo(threshold) < 0) matches[count++] = value;
            }
        } else {
            for (int i = 0; i < values.length; i++) {
                Byte value = values[i];
                inspected++;
                if (value.compareTo(threshold) >= 0) break;
                matches[count++] = value;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);
        System.out.println("Переглянуто елементів: " + inspected);
        for (int i = 0; i < count; i++) System.out.println(matches[i]);
        System.out.println("У масиві знайдено " + count + " входжень (повтори враховано). Умова: " + conditionDescription + ".");
        return count;
    }

    public int analyzeCondition(List<Byte> values, boolean sorted, Byte threshold) {
        String conditionDescription = "значення менше " + threshold;
        String phase = sorted ? "після сортування" : "до сортування";
        System.out.println("List: вибірка даних " + phase + ". Умова: " + conditionDescription);

        String operationName = "вибірка даних: LinkedList<Byte>, " + phase + ", умова: " + conditionDescription;
        long timeStart = System.nanoTime();
        Byte[] matches = new Byte[values.size()];
        int count = 0;
        int inspected = 0;

        if (!sorted) {
            for (Byte value : values) {
                inspected++;
                if (value.compareTo(threshold) < 0) matches[count++] = value;
            }
        } else {
            ListIterator<Byte> cursor = values.listIterator();
            while (cursor.hasNext()) {
                Byte value = cursor.next();
                inspected++;
                if (value.compareTo(threshold) >= 0) break;
                matches[count++] = value;
            }
        }

        PerformanceTracker.displayOperationTime(timeStart, operationName);
        System.out.println("Переглянуто елементів: " + inspected);
        for (int i = 0; i < count; i++) System.out.println(matches[i]);
        System.out.println("У List знайдено " + count + " входжень (повтори враховано). Умова: " + conditionDescription + ".");
        return count;
    }
    // END CONDITION
}