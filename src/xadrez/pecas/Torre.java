package xadrez.pecas;

import tabuleiro.TabuleiroJogo;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez{

	public Torre(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}
	
	

}
