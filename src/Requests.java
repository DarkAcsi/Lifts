import java.util.Random;

/**
 * Класс генерации запросов
 */
public class Requests implements Runnable {
    private int floors, requests;

    /**
     * Конструктор класса
     * @param floors    количество этажей
     * @param requests  количество запросов
     */
    Requests(int floors, int requests){
        this.floors = floors;
        this.requests = requests;
    }

    /**
     * Запуск генерации запросов
     */
    public void run(){
        System.out.println("Requests");
        int direction, start, end;
        Random rand = new Random();
        Request request;
        int req = requests;
        while (req > 0){
            start = rand.nextInt(1,floors);
            end = rand.nextInt(start+1,floors+1);
            direction = rand.nextInt(2);
            if (direction == 0){
                request = new Request(direction, start, end);
            } else {
                request = new Request(direction, end, start);
            }
            Main.queue.add(request);
            req -= 1;
        }
    }

    /**
     * Класс для хранения данных запроса
     */
    public class Request{
        protected int direction, start, end;
        Request(int direction, int start, int end){
            this.direction = direction;
            this.start = start;
            this.end = end;
        }
    }
}
