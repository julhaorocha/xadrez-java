package xadrez.pecas;

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
}
