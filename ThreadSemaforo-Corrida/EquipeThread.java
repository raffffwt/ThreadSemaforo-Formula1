package SO.pag38;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class EquipeThread extends Thread {
    private final Semaphore largada;
    private List<CarroThread> carros = new ArrayList<>();

    private Semaphore semaforoEquipe;
    String nome;

    private Long fastest = 0L;
    private static int lugar = 0;
    public static HashMap<String, Long> chegada = new HashMap<String, Long>();
    private Random random = new Random();

    int carroAtual = 0;

    public EquipeThread(Semaphore semaphore, Semaphore largada, String nome) {
        this.semaforoEquipe = semaphore;
        this.largada = largada;
        this.nome = nome;
        carros.add(new CarroThread(0));
        carros.add(new CarroThread(1));
    }

    @Override
    public void run() {
        try {
            largada.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carros.forEach(carro -> corrida());
        lugar++;
        chegada.put(nome, fastest);
    }

    private void corrida() {
        long tempoI = 0, tempoTot = 0;
        try {
            semaforoEquipe.acquire();
            System.out.printf("Equipe %s entrou na corrida com o carro %d \n\n", nome, carroAtual);
            for (int i = 0; i < 2; i++) {

                tempoI = LocalDateTime.now().getSecond();
                carros.get(carroAtual).volta();
                tempoTot = LocalDateTime.now().getSecond() - tempoI ;

                System.out.printf("O carro %d  da equiope %s completou sua %d° volta em %d Segundos \n", carroAtual, nome, i + 1, tempoTot);
                if (fastest == 0 || tempoTot < fastest) {
                    fastest = tempoTot;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("O carro da equipe %s saiu da corrida \n",nome);
            semaforoEquipe.release();
            try {
                Thread.sleep(random.nextInt(501));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        carroAtual++;
        if (fastest == 0 || tempoTot < fastest) {
            fastest = tempoTot;
        }
    }
}
