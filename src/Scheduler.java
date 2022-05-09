import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<String> filaProcessos = null;
    private List<Integer> burstTime = null;
    private List<Integer> arrivalTime = null;
    private List<Integer> tempoEspera = new ArrayList();
    private List<Integer> qtdExecutada = new ArrayList<>();
    private List<Integer> qtdExecutadaAntes = new ArrayList<>();
    private List<Integer> faltaExecutar = new ArrayList<>();
    private List<Integer> ultimaExecucao = new ArrayList<>();
    private int tempoTotalExecucao = 0;

    public void prepararFila(List filaProcessos, List burstTime, List arrivalTime){
        this.filaProcessos = filaProcessos;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        for (int i = 0; i < filaProcessos.size(); i++) {
            ultimaExecucao.add(i, 0);
            tempoTotalExecucao += (int) burstTime.get(i);
            faltaExecutar.add(i, (Integer) burstTime.get(i));
            qtdExecutada.add(i, 0);
            qtdExecutadaAntes.add(i, 0);
        }
    }

    public void schedulerFCFS(){
        int tempoAtual = 0;

        System.out.println("-------------------- FCFS --------------------\n");
        for (int i=0; i<filaProcessos.size(); i++) {
            tempoEspera.add(i, tempoAtual);
            System.out.println("Tempo atual: " + tempoAtual);
            System.out.println("Executando: " + filaProcessos.get(i));
            System.out.println("");
            tempoAtual += burstTime.get(i);
        }

        System.out.println("");
        float tempoEsperaTotal = 0;
        for (int i = 0; i < tempoEspera.size(); i++) {
            System.out.println("Tempo de espera [" + filaProcessos.get(i) + "]: " + tempoEspera.get(i));
            tempoEsperaTotal += tempoEspera.get(i);
        }

        System.out.println("");
        float tempoEsperaMedio = tempoEsperaTotal/filaProcessos.size();
        System.out.println("Tempo de espera médio: " + tempoEsperaMedio + "\n");
    }

    public void schedulerRoundRobin(){
        int quantum = 4;
        int tempoAtual = 0;

        System.out.println("-------------------- ROUND ROBIN --------------------\n");

        while(tempoTotalExecucao > 0){
            for (int i = 0; i < filaProcessos.size(); i++) {
                if(tempoAtual >= arrivalTime.get(i) && faltaExecutar.get(i) > 0){

                    System.out.println("Tempo atual: " + tempoAtual);
                    System.out.println("Executando: " + filaProcessos.get(i));

                    if(qtdExecutada.get(i) != burstTime.get(i)){
                        ultimaExecucao.set(i, tempoAtual);
                        qtdExecutadaAntes.set(i, qtdExecutada.get(i));
                    }

                    if(quantum > faltaExecutar.get(i)) {
                        tempoAtual = tempoAtual + faltaExecutar.get(i);
                        tempoTotalExecucao = tempoTotalExecucao - faltaExecutar.get(i);

                        faltaExecutar.set(i, 0);
                    }
                    else {
                        tempoAtual += quantum;
                        tempoTotalExecucao -= quantum;

                        int falta = faltaExecutar.get(i);
                        faltaExecutar.set(i, (falta - quantum));
                    }

                    int executado = burstTime.get(i) - faltaExecutar.get(i);
                    qtdExecutada.set(i, executado);

                    System.out.println("Quantidade a ser processada: " + qtdExecutada.get(i));
                    System.out.println("");
                }

                if(tempoTotalExecucao == 0) {
                    break;
                }
            }
        }

        System.out.println("");
        float tempoEsperaTotal = 0;
        for (int i = 0; i < filaProcessos.size(); i++) {
            int espera = ultimaExecucao.get(i) - qtdExecutadaAntes.get(i) - arrivalTime.get(i);
            tempoEspera.add(i, espera);
            tempoEsperaTotal += espera;

            System.out.println("Tempo de espera [" + filaProcessos.get(i) + "]: " + tempoEspera.get(i));
        }

        System.out.println("");
        float tempoEsperaMedio = tempoEsperaTotal/filaProcessos.size();
        System.out.println("Tempo de espera médio: " + tempoEsperaMedio + "\n");
    }
}
