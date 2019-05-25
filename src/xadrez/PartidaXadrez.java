package xadrez;

import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private TabuleiroJogo tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new TabuleiroJogo(8, 8);
		configIni();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++){
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	private void configIni() {
		tabuleiro.lugarPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
		tabuleiro.lugarPeca(new Rei  (tabuleiro, Cor.PRETO), new Posicao(0, 4));
		tabuleiro.lugarPeca(new Rei  (tabuleiro, Cor.BRANCO), new Posicao(7, 4));
	}

}
