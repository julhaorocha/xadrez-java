package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{

	public Peao(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro, cor);	
	}
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiroJogo().getLinhas()][getTabuleiroJogo().getColunas()];	
		
		Posicao p = new Posicao(0, 0);
		
		if(getCor() == Cor.BRANCO) {
			p.setarValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
				p.setarValores(posicao.getLinha() - 2, posicao.getColuna());
				Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
				if(getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p) &&
						getTabuleiroJogo().posicaoExistente(p2) && !getTabuleiroJogo().posicaoOcupada(p2) && getContmov() == 0) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
				if(getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				p.setarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
				if(getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}

			}
			else {
				p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
				if(getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
					p.setarValores(posicao.getLinha() + 2, posicao.getColuna());
					Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
					if(getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p) &&
							getTabuleiroJogo().posicaoExistente(p2) && !getTabuleiroJogo().posicaoOcupada(p2) && getContmov() == 0) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
					p.setarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
					if(getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
					p.setarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
					if(getTabuleiroJogo().posicaoExistente(p) && casaComPecaRival(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}

			}
			
		
		return mat;
	}
	
}
