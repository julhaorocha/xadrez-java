package tabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private TabuleiroJogo tabuleiro;

	public Peca(TabuleiroJogo tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}
	
	protected TabuleiroJogo getTabuleiroJogo() {
		return tabuleiro;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	public boolean algumMovimentoPossivel() {
		boolean[][] mat = movimentosPossiveis();
		for(int i=0; i<mat.length; i++) {
			for(int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}