import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

record Viburnum(String variety, Double clusterWeight) implements Comparable<Viburnum> {
    Viburnum {
        Objects.requireNonNull(variety, "variety не може бути null");
        Objects.requireNonNull(clusterWeight, "clusterWeight не може бути null");
    }

    /**
     * Порівнює об'єкти Viburnum спочатку за сортом (variety) за спаданням,
     * а за однакових сортів — за масою грона (clusterWeight) за спаданням.
     */
    @Override
    public int compareTo(Viburnum other) {
        int varietyComparison = other.variety.compareTo(this.variety);
        if (varietyComparison != 0) {
            return varietyComparison;
        }
        return other.clusterWeight.compareTo(this.clusterWeight);
    }

    @Override
    public String toString() {
        return "Viburnum{variety='" + variety + "', clusterWeight=" + clusterWeight + "}";
    }
}

public class BasicDataOperationsUsingMap {
    private static final Comparator<Map.Entry<Viburnum, String>> CUSTOM_COMPARATOR =
            Map.Entry.comparingByValue();

    private static final Viburnum KEY_TO_SEARCH_AND_DELETE =
            new Viburnum("Ульгень", 29.4);
    private static final Viburnum KEY_TO_ADD =
            new Viburnum("Ягідна", 26.0);

    private static final String VALUE_TO_SEARCH_AND_DELETE = "Мирослав";
    private static final String VALUE_TO_ADD = "Ганна";

    private final HashMap<Viburnum, String> hashMap;
    private final Hashtable<Viburnum, String> hashtable;

    public BasicDataOperationsUsingMap(HashMap<Viburnum, String> hashMap,
                                       Hashtable<Viburnum, String> hashtable) {
        this.hashMap = hashMap;
        this.hashtable = hashtable;
    }

    private void printVariantInfo() {
        System.out.println("Варіант завдання:");
        System.out.println("Назва record-класу рослини: Viburnum (калина)");
        System.out.println("Характеристики рослини: сорт (String variety), маса грона (Double clusterWeight)");
        System.out.println();
        System.out.println("Сортування:");
        System.out.println(" - сорт - за спаданням");
        System.out.println(" - маса грона - за спаданням");
        System.out.println();
        System.out.println("Map<рослина, садівник/садівниця>:");
        for (Map.Entry<Viburnum, String> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println();
        System.out.println("Ключ/значення для додавання: " + KEY_TO_ADD + " -> " + VALUE_TO_ADD);
        System.out.println("Ключ для пошуку і видалення: " + KEY_TO_SEARCH_AND_DELETE);
        System.out.println("Значення для пошуку і видалення: " + VALUE_TO_SEARCH_AND_DELETE);
        System.out.println("Порівняти продуктивність: HashMap з Hashtable");
        System.out.println();
    }

    private void findByKeyInHashMap() {
        long timeStart = System.nanoTime();
        String value = hashMap.get(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем у HashMap");
        System.out.println("Знайдено за ключем у HashMap " + KEY_TO_SEARCH_AND_DELETE + ": " + value);
    }

    private void findMinMaxKeyInHashMap() {
        long timeStart = System.nanoTime();
        if (hashMap.isEmpty()) {
            PerformanceTracker.displayOperationTime(timeStart, "пошук min/max ключа в HashMap");
            return;
        }
        Viburnum minKey = Collections.min(hashMap.keySet());
        Viburnum maxKey = Collections.max(hashMap.keySet());
        PerformanceTracker.displayOperationTime(timeStart, "пошук min/max ключа в HashMap");
        System.out.println("Min ключ у HashMap: " + minKey + ", Max ключ у HashMap: " + maxKey);
    }

    private void printHashMap() {
        long timeStart = System.nanoTime();
        System.out.println("Вміст HashMap:");
        for (Map.Entry<Viburnum, String> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
        PerformanceTracker.displayOperationTime(timeStart, "виведення HashMap");
    }

    private void sortHashMapByValue() {
        long timeStart = System.nanoTime();
        List<Map.Entry<Viburnum, String>> entries = new ArrayList<>(hashMap.entrySet());
        entries.sort(CUSTOM_COMPARATOR);
        PerformanceTracker.displayOperationTime(timeStart, "сортування HashMap за значенням");
    }

    private void addEntryToHashMap() {
        long timeStart = System.nanoTime();
        hashMap.put(KEY_TO_ADD, VALUE_TO_ADD);
        PerformanceTracker.displayOperationTime(timeStart, "додавання елемента в HashMap");
        System.out.println("Додано запис у HashMap: " + KEY_TO_ADD + " -> " + VALUE_TO_ADD);
    }

    private void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();
        String removed = hashMap.remove(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");
        System.out.println("Видалено з HashMap за ключем " + KEY_TO_SEARCH_AND_DELETE + ": " + removed);
    }

    private void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();
        List<Viburnum> keysToRemove = new ArrayList<>();
        for (Map.Entry<Viburnum, String> entry : hashMap.entrySet()) {
            if (VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue())) {
                keysToRemove.add(entry.getKey());
            }
        }
        for (Viburnum key : keysToRemove) {
            hashMap.remove(key);
        }
        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");
        System.out.println("Видалено з HashMap " + keysToRemove.size()
                + " записів з садівником (садівницею) '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    private void findByKeyInHashtable() {
        long timeStart = System.nanoTime();
        String value = hashtable.get(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем у Hashtable");
        System.out.println("Знайдено за ключем у Hashtable " + KEY_TO_SEARCH_AND_DELETE + ": " + value);
    }

    private void findMinMaxKeyInHashtable() {
        long timeStart = System.nanoTime();
        if (hashtable.isEmpty()) {
            PerformanceTracker.displayOperationTime(timeStart, "пошук min/max ключа в Hashtable");
            return;
        }
        Viburnum minKey = Collections.min(hashtable.keySet());
        Viburnum maxKey = Collections.max(hashtable.keySet());
        PerformanceTracker.displayOperationTime(timeStart, "пошук min/max ключа в Hashtable");
        System.out.println("Min ключ у Hashtable: " + minKey + ", Max ключ у Hashtable: " + maxKey);
    }

    private void printHashtable() {
        long timeStart = System.nanoTime();
        System.out.println("Вміст Hashtable:");
        for (Map.Entry<Viburnum, String> entry : hashtable.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
        PerformanceTracker.displayOperationTime(timeStart, "виведення Hashtable");
    }

    private void sortHashtable() {
        long timeStart = System.nanoTime();
        List<Viburnum> sortedKeys = new ArrayList<>(hashtable.keySet());
        Collections.sort(sortedKeys);

        Hashtable<Viburnum, String> sortedHashtable = new Hashtable<>();
        for (Viburnum key : sortedKeys) {
            sortedHashtable.put(key, hashtable.get(key));
        }
        hashtable.clear();
        hashtable.putAll(sortedHashtable);

        PerformanceTracker.displayOperationTime(timeStart, "сортування Hashtable за ключами");
    }

    private void addEntryToHashtable() {
        long timeStart = System.nanoTime();
        hashtable.put(KEY_TO_ADD, VALUE_TO_ADD);
        PerformanceTracker.displayOperationTime(timeStart, "додавання елемента в Hashtable");
        System.out.println("Додано запис у Hashtable: " + KEY_TO_ADD + " -> " + VALUE_TO_ADD);
    }

    private void removeByKeyFromHashtable() {
        long timeStart = System.nanoTime();
        String removed = hashtable.remove(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з Hashtable");
        System.out.println("Видалено з Hashtable за ключем " + KEY_TO_SEARCH_AND_DELETE + ": " + removed);
    }

    private void removeByValueFromHashtable() {
        long timeStart = System.nanoTime();
        List<Viburnum> keysToRemove = new ArrayList<>();
        for (Map.Entry<Viburnum, String> entry : hashtable.entrySet()) {
            if (VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue())) {
                keysToRemove.add(entry.getKey());
            }
        }
        for (Viburnum key : keysToRemove) {
            hashtable.remove(key);
        }
        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з Hashtable");
        System.out.println("Видалено з Hashtable " + keysToRemove.size()
                + " записів з садівником (садівницею) '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    private void findByValueInHashMap() {
        long timeStart = System.nanoTime();
        List<Map.Entry<Viburnum, String>> entries = new ArrayList<>(hashMap.entrySet());
        entries.sort(CUSTOM_COMPARATOR);
        Map.Entry<Viburnum, String> target = Map.entry(KEY_TO_SEARCH_AND_DELETE, VALUE_TO_SEARCH_AND_DELETE);
        int index = Collections.binarySearch(entries, target, CUSTOM_COMPARATOR);
        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням у HashMap");
        System.out.println("Пошук за значенням '" + VALUE_TO_SEARCH_AND_DELETE
                + "' у HashMap (binarySearch index): " + index);
    }

    private void findByValueInHashtable() {
        long timeStart = System.nanoTime();
        List<Map.Entry<Viburnum, String>> entries = new ArrayList<>(hashtable.entrySet());
        entries.sort(CUSTOM_COMPARATOR);
        Map.Entry<Viburnum, String> target = Map.entry(KEY_TO_SEARCH_AND_DELETE, VALUE_TO_SEARCH_AND_DELETE);
        int index = Collections.binarySearch(entries, target, CUSTOM_COMPARATOR);
        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням у Hashtable");
        System.out.println("Пошук за значенням '" + VALUE_TO_SEARCH_AND_DELETE
                + "' у Hashtable (binarySearch index): " + index);
    }

    public void executeDataOperations() {
        printVariantInfo();

        // 1. Блок для HashMap
        findByKeyInHashMap();
        findByValueInHashMap();
        findMinMaxKeyInHashMap();
        printHashMap();
        sortHashMapByValue();
        printHashMap();
        findByKeyInHashMap();
        findByValueInHashMap();
        findMinMaxKeyInHashMap();
        addEntryToHashMap();
        removeByKeyFromHashMap();
        removeByValueFromHashMap();

        // 2. Блок для Hashtable
        findByKeyInHashtable();
        findByValueInHashtable();
        findMinMaxKeyInHashtable();
        printHashtable();
        sortHashtable();
        printHashtable();
        findByKeyInHashtable();
        findByValueInHashtable();
        findMinMaxKeyInHashtable();
        addEntryToHashtable();
        removeByKeyFromHashtable();
        removeByValueFromHashtable();
    }

    public static void main(String[] args) {
        Map<Viburnum, String> viburnums = Map.ofEntries(
                Map.entry(new Viburnum("Червоне намисто", 25.5), "Мирослав"),
                Map.entry(new Viburnum("Ульгень", 32.0), "Ганна"),
                Map.entry(new Viburnum("Гранатовий браслет", 40.8), "Роксолана"),
                Map.entry(new Viburnum("Червоне намисто", 28.3), "Іван"),
                Map.entry(new Viburnum("Елегантна", 22.1), "Мирослав"),
                Map.entry(new Viburnum("Соузга", 35.6), "Тетяна"),
                Map.entry(new Viburnum("Ульгень", 29.4), "Богдана"),
                Map.entry(new Viburnum("Рубінова", 31.2), "Северин"),
                Map.entry(new Viburnum("Калинова сопілка", 27.7), "Віра"),
                Map.entry(new Viburnum("Гранатовий браслет", 38.0), "Святослав")
        );

        HashMap<Viburnum, String> hashMap = new HashMap<>(viburnums);
        Hashtable<Viburnum, String> hashtable = new Hashtable<>(viburnums);

        BasicDataOperationsUsingMap operations = new BasicDataOperationsUsingMap(hashMap, hashtable);
        operations.executeDataOperations();
    }
}