package xadrez;


import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private TabuleiroJogo tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new TabuleiroJogo(8, 8);
		configIni();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++){
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.cvPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez movimentoPeca(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.cvPosicao();
		Posicao	destino = posicaoDestino.cvPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (PecaXadrez)pecaCapturada;
	}
	private Peca  fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);
		return pecaCapturada;
	}
	private void validarPosicaoOrigem(Posicao posicao) {
		
		if (!tabuleiro.posicaoOcupada(posicao)) {
			throw new XadrezException("Não existe peça nesta posição");
		}
		if (!tabuleiro.peca(posicao).algumMovimentoPossivel()) {
			throw new XadrezException("Não existe movimento possiveis com peça escolhida");
			
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para a posicao de destino");
		}
	}
	
	private void lugarNovaPeca(char coluna,int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).cvPosicao());
	}
	private void configIni() {
		lugarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		lugarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
	}

}
