package br.com.wferreiracosta.visao;

import br.com.wferreiracosta.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class PainelTabuleiro extends JPanel {

    public PainelTabuleiro(Tabuleiro tabuleiro){
        this.setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
        int total = tabuleiro.getLinhas() * tabuleiro.getColunas();
        tabuleiro.paraCadaCampo(c -> this.add(new BotaoCampo(c)));
        tabuleiro.registrarObservador(e -> {
            SwingUtilities.invokeLater(() -> {
                if(e.isGanhou()){
                    JOptionPane.showMessageDialog(this, "Ganhou!!!");
                } else {
                    JOptionPane.showMessageDialog(this, "Perdeu!!!");
                }
                tabuleiro.reiniciar();
            });
        });
    }

}
