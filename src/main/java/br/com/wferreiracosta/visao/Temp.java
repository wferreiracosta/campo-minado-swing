package br.com.wferreiracosta.visao;

import br.com.wferreiracosta.modelo.Tabuleiro;

public class Temp {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(3, 3, 9);

        tabuleiro.registrarObservador(e -> {
            if (e == true){
                System.out.println("Ganhou...");
            } else {
                System.out.println("Perdeu...");
            }
        });

        tabuleiro.altenarMarcacao(0, 0);
        tabuleiro.altenarMarcacao(0,1);
        tabuleiro.altenarMarcacao(0,2);
        tabuleiro.altenarMarcacao(1,0);
        tabuleiro.altenarMarcacao(1,1);
        tabuleiro.altenarMarcacao(1,2);
        tabuleiro.altenarMarcacao(2,0);
        tabuleiro.altenarMarcacao(2,1);
        tabuleiro.abrir(2,2);
//        tabuleiro.altenarMarcacao(2,2);
    }
}
