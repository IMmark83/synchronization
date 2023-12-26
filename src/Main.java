import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    private final static int AMOUNT_OF_THREAD = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < AMOUNT_OF_THREAD; i++) {
            new Thread(() -> {

                int R_count = 0;
                String route = generateRoute("RLRFR", 100);
                for (char c : route.toCharArray()) {
                    if (c == 'R') {
                        R_count++;
                    }
                }

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(R_count)) {
                        sizeToFreq.put(R_count, sizeToFreq.get(R_count) + 1);
                    } else {
                        sizeToFreq.put(R_count, 1);
                    }
                }
            }).start();
        }
        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Частое количество повторений R: " + max.getKey() + "(встретилось " + max.getValue() + "раз)");
        System.out.println("Другие размеры: ");

        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println((" -> " + e.getKey() + "(" + (e.getValue() + "раз)"))));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}