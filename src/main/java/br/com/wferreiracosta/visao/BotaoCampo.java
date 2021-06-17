package br.com.wferreiracosta.visao;

import br.com.wferreiracosta.modelo.Campo;
import br.com.wferreiracosta.modelo.CampoEvento;
import br.com.wferreiracosta.modelo.CampoObservador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

    private final Color BG_PADRAO = new Color(184,184,184);
    private final Color BG_MARCAR = new Color(8,179,247);
    private final Color BG_EXPLODIR = new Color(189,66,68);
    private final Color TEXTO_VERDE = new Color(0,100,0);

    private Campo campo;

    public BotaoCampo(Campo campo){
        this.campo = campo;
        this.setBackground(this.BG_PADRAO);
        this.setBorder(BorderFactory.createBevelBorder(0));
        this.addMouseListener(this);
        campo.registrarObservador(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento) {
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
        }

        SwingUtilities.invokeLater(() -> {
            repaint();
            validate();
        });
    }

    private void aplicarEstiloPadrao() {
        this.setBackground(this.BG_PADRAO);
        this.setBorder(BorderFactory.createBevelBorder(0));
        this.setText("");
    }

    private void aplicarEstiloExplodir() {
        this.setBackground(this.BG_EXPLODIR);
        this.setForeground(Color.black);
        this.setText("X");
    }

    private void aplicarEstiloMarcar() {
        this.setBackground(this.BG_MARCAR);
        this.setForeground(Color.black);
        this.setText("M");
    }

    private void aplicarEstiloAbrir() {
        this.setBorder(BorderFactory.createLineBorder(Color.gray));

        if (this.campo.isMinado()){
            this.setBackground(this.BG_EXPLODIR);
            return;
        }

        this.setBackground(this.BG_PADRAO);
        switch (this.campo.minasNaVizinhanca()) {
            case 1:
                this.setForeground(this.TEXTO_VERDE);
                break;
            case 2:
                this.setForeground(Color.blue);
                break;
            case 3:
                this.setForeground(Color.yellow);
                break;
            case 4: case 5: case 6:
                this.setForeground(Color.red);
                break;
            default:
                this.setForeground(Color.pink);
        }
        String valor = !this.campo.vizinhacaoSegura() ?
                campo.minasNaVizinhanca() + "" : "";
        this.setText(valor);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1){
            this.campo.abrir();
        } else {
            this.campo.alternarMarcacao();
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
