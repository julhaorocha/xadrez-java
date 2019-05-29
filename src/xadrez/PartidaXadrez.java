package xadrez;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int vez;
	private Cor jogadorAtual;
	private TabuleiroJogo tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new TabuleiroJogo(8, 8);
		vez = 1;
		jogadorAtual = Cor.BRANCO;
		configIni();
	}
	
	public int getVez() {
		return vez;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	public boolean getCheck() {
		return check;
	}
	public boolean getCheckMate() {
		return checkMate;
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
		
		if(testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Voce não pode se colocar em CHECK!");
		}
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else{
			proximaVez();
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	private Peca  fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		p.admov();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.lugarPeca(p, destino);
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);	
		}
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
		p.submov();
		tabuleiro.lugarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);	
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		
		if (!tabuleiro.posicaoOcupada(posicao)) {
			throw new XadrezException("Nao existe peca nesta posicao");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("Peca escolhida nao e sua");
		}
		if (!tabuleiro.peca(posicao).algumMovimentoPossivel()) {
			throw new XadrezException("Nao existe movimentos possiveis com peca escolhida");	
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezException("A peça escolhida não pode se mover para a posicao de destino");
		}
	}
	private void proximaVez() {
		vez++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return(cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if(p instanceof Rei) {
				return(PecaXadrez)p;
			}
			
		}
		throw new IllegalStateException("Nao existe o rei " + cor + " no tabuleiro");
	}
	
	private boolean testarCheck(Cor cor) {
		Posicao reiPosicao = rei(cor).getPosicaoXadrez().cvPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x->((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if(mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	private boolean testarCheckMate(Cor cor) {
		if(!testarCheck(cor)) {
			return false;	
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean [][] mat = p.movimentosPossiveis();
			for(int i=0; i<tabuleiro.getLinhas(); i++) {
				for(int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().cvPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeCheck = testarCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
				
			}
		}
		return true;
	}
	
	
	private void lugarNovaPeca(char coluna,int linha, PecaXadrez peca) {
		tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).cvPosicao());
		pecasNoTabuleiro.add(peca);
	}
	private void configIni() {
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));
	

		lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
		lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
		lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
		lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
		lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
		lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
		lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
	}

}
