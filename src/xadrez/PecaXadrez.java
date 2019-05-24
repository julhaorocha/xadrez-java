package xadrez;

import tabuleiro.Peca;
import tabuleiro.TabuleiroJogo;

public class PecaXadrez extends Peca{
	
	private Cor cor;

	public PecaXadrez(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}

}
