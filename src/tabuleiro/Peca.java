package tabuleiro;

public class Peca {

	protected Posicao posicao;
	private TabuleiroJogo tabuleiro;

	public Peca(TabuleiroJogo tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}
	
	protected TabuleiroJogo getTabuleiroJogo() {
		return tabuleiro;
	}
		
}