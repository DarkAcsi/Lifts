import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

/**
 * Главный класс. Запускает работу генератора запросов и распределителя лифтов
 */
public class Main {
    static private Scanner sc = new Scanner(System.in);
    static protected Queue<Requests.Request> queue = new LinkedList();
    static int floors, elevators;
    static public int requests;
    static int cnt = 0;

    /**
     * Получение переменных данных: количество этажей, лифтов и запросов
     */
    public static void Input(){
        try {
            String s;
            System.out.print("Количество этажей: ");
            s = sc.nextLine();
            floors = Integer.parseInt(s);
            System.out.print("Количество лифтов: ");
            s = sc.nextLine();
            elevators = Integer.parseInt(s);
            System.out.print("Количество запросов: ");
            s = sc.nextLine();
            requests = Integer.parseInt(s);
            if (floors <= 0 || elevators <= 0 || requests <= 0){
                System.out.println("Значения должны больше нуля. Попробуйте снова");
                Input();
            }
        } catch (Exception e){
            System.out.println("Неправильный ввод. Попробуйте снова");
            Input();
        }
    }

    /**
     * После запуска программы первый раз есть возможность начать сначала командой continue,
     * а также завершить командой exit (регистр значения не имеет)
     * @param args
     */
    public static void main(String[] args) {
        Input();
        Thread res = new Thread(new Requests(floors, requests));
        Thread elev = new Thread(new Elevators(elevators, floors));
        res.start();
        elev.start();
        String s;
        while (sc.hasNext()){
            if (cnt == requests) {
                boolean ex = false;
                while (true) {
                    System.out.println("Continue or exit?");
                    s = sc.nextLine().toLowerCase();
                    if (s.equals("exit")) {
                        ex = true;
                        break;
                    } else if (s.equals("continue")) {
                        break;
                    }
                }
                if (ex) {
                    break;
                }
                cnt = 0;
                Input();
                res = new Thread(new Requests(floors, requests));
                elev = new Thread(new Elevators(elevators, floors));
                res.start();
                elev.start();
            }
        }
    }
}