package xadrez.pecas;


import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Rei(TabuleiroJogo tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiroJogo().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	private boolean testeTorreRoque(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiroJogo().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContmov() == 0;
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
		//movimento especial roque
		if (getContmov() == 0 && !partidaXadrez.getCheck()) {
			//movimento  especial roque do lado do rei
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testeTorreRoque(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if(getTabuleiroJogo().peca(p1) == null && getTabuleiroJogo().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			//movimento  especial roque do lado da rainha
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testeTorreRoque(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if(getTabuleiroJogo().peca(p1) == null && getTabuleiroJogo().peca(p2) == null && getTabuleiroJogo().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}
		
	
		
		
		
		
		
		return mat;
	}
}
