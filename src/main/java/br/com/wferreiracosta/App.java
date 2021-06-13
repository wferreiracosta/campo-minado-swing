package br.com.wferreiracosta;

import br.com.wferreiracosta.modelo.Tabuleiro;
import br.com.wferreiracosta.visao.TabuleiroConsole;

public class App {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);
        new TabuleiroConsole(tabuleiro);
    }
}
