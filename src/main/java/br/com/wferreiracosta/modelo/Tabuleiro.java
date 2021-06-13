package br.com.wferreiracosta.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Setter
@Getter
public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;
    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas){
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        
        this.gerarCampos();
        this.associarVizinhos();
        this.sortearMinas();
    }

    public void abrir(int linha, int coluna){
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(c -> c.abrir());
        } catch (Exception e){
            // FIXME Ajusta a implementação do método abrir
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void altenarMarcacao(int linha, int coluna){
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void sortearMinas() {
        long minasArmadas = 0L;
        Predicate<Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int) (Math.random() * campos.size());
            this.campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < this.getMinas());
    }

    private void associarVizinhos() {
        for(Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void gerarCampos() {
        for(int linha = 0; linha < linhas; linha++){
            for (int coluna = 0; coluna < colunas; coluna++){
                this.campos.add(new Campo(linha, coluna));
            }
        }
    }

    public boolean objetivoAlcancado(){
        return this.campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar(){
        this.campos.stream().forEach(c -> c.reiniciar());
        this.sortearMinas();
    }
    
}
