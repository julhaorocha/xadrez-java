package xadrez.pecas;


import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {

	public Cavalo(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}
	@Override
	public String toString() {
		return "C";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiroJogo().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiroJogo().getLinhas()][getTabuleiroJogo().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		

		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
	
		p.setarValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setarValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setarValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setarValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if(getTabuleiroJogo().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		
		
		
		
		
		return mat;
	}
}

