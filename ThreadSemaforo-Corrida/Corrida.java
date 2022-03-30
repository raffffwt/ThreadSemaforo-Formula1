package SO.pag38;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.concurrent.Semaphore;

public class Corrida {

    public static void main(String[] args) throws InterruptedException {

        Semaphore pista = new Semaphore(5);
        Semaphore largada = new Semaphore(0);
        String[] nomes = {"Ferrari", "Mercedes","BMW", "Lamborghini","Porsche","Acabou","A Criatividade"};
        Thread equipe;

        for(int i=0; i<7; i++){
            equipe = new EquipeThread(pista, largada, nomes[i]);
            equipe.start();
        }
        System.out.println("Largada em: \n3... ");
        Thread.sleep(1000);
        System.out.println("2...");
        Thread.sleep(1000);
        System.out.println("1...");
        Thread.sleep(1000);
        System.out.println("FOI DADA A LARGADA");
        largada.release(7);

        Thread.sleep(35000);
        Long tempo;
        System.out.println("\n\n\nLinha de chegada");
        for (int i=1; i<=7; i++) {

             tempo = EquipeThread.chegada.get(nomes[i-1]);
            System.out.println("Equipe "+nomes[i-1]+"  Melhor tempo:"+tempo+"°");
        }
    }


}
