package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez{

	public Rainha(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiroJogo().getLinhas()][getTabuleiroJogo().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		// acima
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// esquerda
		p.setarValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//direita
		p.setarValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//Baixo
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
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
