package br.com.wferreiracosta;

import br.com.wferreiracosta.modelo.Tabuleiro;
import br.com.wferreiracosta.visao.PainelTabuleiro;

import javax.swing.*;

public class App extends JFrame {

    public App(){
        Tabuleiro tabuleiro = new Tabuleiro(16, 30, 15);
        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro);

        this.add(painelTabuleiro);

        this.setTitle("Campo Minado");
        this.setSize(690, 438);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new App();
    }
}
