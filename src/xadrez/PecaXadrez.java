package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;

public abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	private int contMov;

	public PecaXadrez(TabuleiroJogo tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	public int getContmov() {
		return contMov;
	}
	public void admov() {
		contMov++;
	}
	public void submov() {
		contMov--;
	}
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.vcPosicao(posicao);
	}
	
	protected boolean casaComPecaRival(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiroJogo().peca(posicao);
		return p != null && p.getCor() != cor;
	}

}
