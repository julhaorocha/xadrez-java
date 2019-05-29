package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez{

	public Bispo(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiroJogo().getLinhas()][getTabuleiroJogo().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Noroeste
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setarValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// nordeste
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setarValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//sudeste
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setarValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Sudoeste
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() -1);
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setarValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		return mat;
	}

}
