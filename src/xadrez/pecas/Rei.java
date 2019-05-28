package xadrez.pecas;


import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiroJogo().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiroJogo().getLinhas()][getTabuleiroJogo().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//acima
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna());
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//abaixo
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//esquerda
		p.setarValores(posicao.getLinha(), posicao.getColuna() - 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//direita
		p.setarValores(posicao.getLinha(), posicao.getColuna() + 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//noroeste
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//nordeste
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//sudoeste
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//sudeste
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		
		
		
		
		return mat;
	}
}
