package br.com.wferreiracosta.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registrarObservador(CampoObservador observador) {
        observadores.add(observador);
    }

    private void notificarObservadores(CampoEvento evento) {
        observadores.stream()
                .forEach(o -> o.eventoOcorreu(this, evento));
    }

    public boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternarMarcacao() {
        if (!isAberto()) {
            marcado = !marcado;
            if (marcado) {
                this.notificarObservadores(CampoEvento.MARCAR);
            } else {
                this.notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir() {
        if (!isAberto() && !isMarcado()) {
            if (isMinado()) {
                this.notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }

            this.setAberto(true);

            if (vizinhacaoSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }

            return true;
        }
        return false;
    }

    public boolean vizinhacaoSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public void minar() {
        setMinado(true);
    }

    public boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public int minasNaVizinhanca() {
        return (int) vizinhos.stream().filter(v -> v.minado).count();
    }

    public void reiniciar() {
        this.setAberto(false);
        this.setMinado(false);
        this.setMarcado(false);
        this.notificarObservadores(CampoEvento.REINICIAR);
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
        if (aberto) {
            this.notificarObservadores(CampoEvento.ABRIR);
        }
    }
}
