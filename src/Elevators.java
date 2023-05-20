/**
 * Класс для распределения запросов по лифтам
 */
public class Elevators implements Runnable{
    private int cnt_lift, floors;

    /**
     * Конструктор класса
     * @param cnt_lift  Количество лифтов
     * @param floors    Количество этажей
     */
    Elevators(int cnt_lift, int floors){
        this.cnt_lift = cnt_lift;
        this.floors = floors;
    }

    /**
     * Запускае распределение запросов по лифтам и выводит результаты на консоль
     */
    public void run() {
        System.out.println("Elevators");
        Elevator[] lift = new Elevator[cnt_lift];
        for (int i = 0; i < cnt_lift; i++){
            lift[i] = new Elevator();
        }
        while (Main.requests != Main.cnt){
            Requests.Request req = Main.queue.peek();
            if (req != null){
                Main.queue.remove();
                int minn = 1000000000, ind = 0;
                for (int i = 0; i < cnt_lift; i++){
                    if (Takts(lift[i],req) < minn){
                        minn = Takts(lift[i],req);
                        ind = i;
                    }
                }
                if (lift[ind].direction == 0 && req.direction == 0){
                    if (lift[ind].start <= req.start && lift[ind].end >= req.end){
                        req.end = lift[ind].end;
                    }
                } else {
                    if (lift[ind].start >= req.start && lift[ind].end <= req.end){
                        req.end = lift[ind].end;
                    }
                }
                lift[ind] = new Elevator(req.direction,req.start,req.end);
                System.out.println((Main.cnt+1) + "\tЛифт № " + (ind+1) + "\tПуть лифта:\t " + req.direction
                        + "\t" + req.start + "\t" + req.end);
                Main.cnt += 1;
            }
        }
    }

    /**
     * Подсчитывает количестово этажей, которые проедет лифт,
     * учитывая местоположение лифта
     * @param el    местоположение лифта
     * @param r     следующий запрос
     * @return      количество этажей до следующего запроса
     */
    private int Takts(Elevator el, Requests.Request r){
        if (el.direction == 0) {
            if (r.direction == 0 && el.start <= r.start) {
                return r.start - el.start;
            } else if (r.direction == 1 && el.end <= r.start){
                return r.start - el.start;
            }
        } else {
            if (r.direction == 1 && r.start <= el.start) {
                return el.start - r.start;
            } else if (r.direction == 0 && r.start <= el.end){
                return el.start - r.start;
            }
        }
        return Math.abs(el.end * 2 - el.start - r.start);
    }

    /**
     * Приватный класс, содержащий местоположение лифта
     */
    private class Elevator{
        private int direction, start, end;
        /** Пустой конструктор */
        Elevator(){
            this(0,1,0);
        }
        /** Коструктор с данными о пути */
        Elevator(int direction, int start, int end){
            this.direction = direction;
            this.start = start;
            this.end = end;
        }
    }
}
