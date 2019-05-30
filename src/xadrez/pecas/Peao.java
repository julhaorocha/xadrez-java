package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{
	
	private PartidaXadrez partidaXadrez;

	public Peao(TabuleiroJogo tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);	
		this.partidaXadrez = partidaXadrez;
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
				//#Movimento especial enpassant branca
				if (posicao.getLinha() == 3) {
					Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
					if (getTabuleiroJogo().posicaoExistente(esquerda) && casaComPecaRival(esquerda) 
							&& getTabuleiroJogo().peca(esquerda) == partidaXadrez.getEnPassantVuneravel()) {
						mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
					}
					Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
					if (getTabuleiroJogo().posicaoExistente(direita) && casaComPecaRival(direita) 
							&& getTabuleiroJogo().peca(direita) == partidaXadrez.getEnPassantVuneravel()) {
						mat[direita.getLinha() - 1][direita.getColuna()] = true;
					}
				}

			}
			else {
				p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
				if(getTabuleiroJogo().posicaoExistente(p) && !getTabuleiroJogo().posicaoOcupada(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
					p.setarValores(posicao.getLinha() + 2, posicao.getColuna());
					Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
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
					//Movimento especial enpassant preta
					if (posicao.getLinha() == 4) {
						Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
						if (getTabuleiroJogo().posicaoExistente(esquerda) && casaComPecaRival(esquerda) 
								&& getTabuleiroJogo().peca(esquerda) == partidaXadrez.getEnPassantVuneravel()) {
							mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
						}
						Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
						if (getTabuleiroJogo().posicaoExistente(direita) && casaComPecaRival(direita) 
								&& getTabuleiroJogo().peca(direita) == partidaXadrez.getEnPassantVuneravel()) {
							mat[direita.getLinha() + 1][direita.getColuna()] = true;
						}
					}

			}
			
		
		return mat;
	}
	
}
