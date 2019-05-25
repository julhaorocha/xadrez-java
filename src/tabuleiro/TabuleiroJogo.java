package tabuleiro;

public class TabuleiroJogo {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	
	public TabuleiroJogo(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar tabuleirio: O tabuleiro deve conter pelo menos"
					+ " 1 linha e 1 coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if (!posicaoExistente(linha, coluna)) {
			throw new TabuleiroException("Posi��o inexistente no tabuleiro");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	public void lugarPeca(Peca peca, Posicao posicao){
		if (posicaoOcupada(posicao)) {
			throw new TabuleiroException("Ja existe um pe�a nesta posi��o " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	public boolean posicaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}
	public boolean posicaoOcupada(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new TabuleiroException("Posi��o inexistente no tabuleiro");
		}
		return peca(posicao) != null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
