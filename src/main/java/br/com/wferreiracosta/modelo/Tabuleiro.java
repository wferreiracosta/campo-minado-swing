package br.com.wferreiracosta.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Setter
@Getter
public class Tabuleiro implements CampoObservador {

    private final int linhas;
    private final int colunas;
    private final int minas;

    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas){
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        this.gerarCampos();
        this.associarVizinhos();
        this.sortearMinas();
    }

    public void paraCadaCampo(Consumer<Campo> funcao){
        this.campos.forEach(funcao);
    }

    public void registrarObservador(Consumer<ResultadoEvento> observador){
        this.observadores.add(observador);
    }

    public void notificarObservadores(boolean resultado){
        observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
    }

    public void abrir(int linha, int coluna){
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.abrir());
    }

    public void alternarMarcacao(int linha, int coluna){
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void gerarCampos() {
        for(int linha = 0; linha < linhas; linha++){
            for (int coluna = 0; coluna < colunas; coluna++){
                Campo campo = new Campo(linha, coluna);
                campo.registrarObservador(this);
                this.campos.add(campo);
            }
        }
    }

    private void associarVizinhos() {
        for(Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
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

    public boolean objetivoAlcancado(){
        return this.campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar(){
        this.campos.stream().forEach(c -> c.reiniciar());
        this.sortearMinas();
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if (evento == CampoEvento.EXPLODIR) {
            this.mostrarMinas();
            this.notificarObservadores(false);
        } else if (this.objetivoAlcancado()) {
            this.notificarObservadores(true);
        }
    }

    private void mostrarMinas(){
        this.campos.stream()
                .filter(c -> c.isMinado())
                .filter(c -> !c.isMarcado())
                .forEach(c -> c.setAberto(true));
    }
}
