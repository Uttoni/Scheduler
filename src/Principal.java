import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        List<String> filaProcessos = new ArrayList();
        List<Integer> burstTime = new ArrayList();
        List<Integer> arrivalTime = new ArrayList();
        Scheduler scheduler = new Scheduler();


        //EXEMPLO PREEMPTIVO
        filaProcessos.addAll(Arrays.asList("P1", "P2", "P3"));
        burstTime.addAll(Arrays.asList(5, 6, 8));
        arrivalTime.addAll(Arrays.asList(0, 0, 0));


        scheduler.prepararPreemptivo(filaProcessos, burstTime, arrivalTime);
        scheduler.ordenarRoundRobin();
    }
}
