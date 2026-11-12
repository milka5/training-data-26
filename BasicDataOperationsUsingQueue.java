import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationsUsingQueue реалізує роботу з колекціями типу Queue для Byte.
 *
 * <p>Основні функції класу:</p>
 * <ul>
 *   <li>{@link #runDataProcessing()} - Запускає комплекс операцій з даними.</li>
 *   <li>{@link #findInQueue()} - Пошук значення в черзі Byte.</li>
 *   <li>{@link #locateMinMaxInQueue()} - Знаходить граничні значення в черзі.</li>
 *   <li>{@link #performQueueOperations()} - Виконує операції peek і poll з чергою.</li>
 * </ul>
 *
 */
public class BasicDataOperationsUsingQueue {
    private Byte valueToSearch;
    private Queue<Byte> dataQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     *
     * @param valueToSearch Значення для пошуку
     * @param dataArray Масив Byte
     */
    BasicDataOperationsUsingQueue(Byte valueToSearch, Byte[] dataArray) {
        this.valueToSearch = valueToSearch;
        this.dataQueue = new PriorityQueue<>(Arrays.asList(dataArray));
    }

    /**
     * Запускає комплексну обробку даних з використанням черги.
     *
     * Метод виконує пошук і визначення граничних значень у черзі Byte,
     * а потім операції peek і poll.
     */
    public void runDataProcessing() {
        findInQueue();
        locateMinMaxInQueue();

        // зберігаємо до файлу відсортовані значення з PriorityQueue (з повторами);
        // порядок обходу черги не є відсортованим, тому сортуємо копію її вмісту
        Byte[] sortedQueueValues = dataQueue.toArray(new Byte[0]);
        Arrays.sort(sortedQueueValues);
        DataFileHandler.writeArrayToFile(sortedQueueValues, BasicDataOperations.SORTED_FILE_PREFIX + ".queue.data");

        // Вилучення виконуємо після пошуку і запису файлу, щоб вони працювали з повним складом черги.
        performQueueOperations();
    }

    /**
     * Здійснює пошук конкретного значення в черзі.
     */
    private void findInQueue() {
        // назву операції будуємо до заміру, щоб склеювання рядка не потрапило у вимір
        String operationName = "пошук елемента '" + valueToSearch + "' в PriorityQueue<Byte>";
        // вимірюємо час пошуку в черзі
        long timeStart = System.nanoTime();

        boolean elementExists = this.dataQueue.contains(valueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, operationName);

        if (elementExists) {
            System.out.println("Елемент '" + valueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Елемент '" + valueToSearch + "' відсутній в Queue.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в черзі Byte.
     */
    private void locateMinMaxInQueue() {
        if (dataQueue == null || dataQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // відстежуємо час пошуку граничних значень
        long timeStart = System.nanoTime();

        Byte minValue = Collections.min(dataQueue);
        Byte maxValue = Collections.max(dataQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального значення в PriorityQueue<Byte>");

        System.out.println("Найменше значення в Queue: " + minValue);
        System.out.println("Найбільше значення в Queue: " + maxValue);
    }

    /**
     * Виконує операції peek і poll з чергою Byte.
     */
    private void performQueueOperations() {
        if (dataQueue == null || dataQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // peek() повертає корінь купи, тобто найменше значення, не видаляючи його; O(1).
        Byte headElement = dataQueue.peek();
        System.out.println("Головний елемент черги (peek): " + headElement);

        // poll() видаляє корінь: на його місце стає останній елемент купи і «просідає» вниз,
        // доки не відновиться порядок купи; O(log n). Новим коренем стає наступне найменше значення.
        headElement = dataQueue.poll();
        System.out.println("Видалений елемент черги (poll): " + headElement);

        headElement = dataQueue.peek();
        System.out.println("Новий головний елемент черги: " + headElement);
    }
}