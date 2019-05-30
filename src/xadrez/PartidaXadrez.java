package xadrez;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int vez;
	private Cor jogadorAtual;
	private TabuleiroJogo tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez enPassantVuneravel;
	private PecaXadrez promovido;
	
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
	public PecaXadrez getEnPassantVuneravel() {
		return enPassantVuneravel;
	}
	public PecaXadrez getPromovido() {
		return promovido;
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
			throw new XadrezException("Voce nao pode se colocar em CHECK!");
		}
		
		PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);
		
		//#Movimento especial promoção
		promovido = null;
		if (pecaMovida instanceof Peao) {
			if ((pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promovido = (PecaXadrez)tabuleiro.peca(destino);
				promovido = trocaPecaPromovida("Q");	
			}
		}
				
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else{
			proximaVez();
		}
		
		//#Movimento Especial en passant
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVuneravel = pecaMovida;
		}
		else {
			enPassantVuneravel = null;
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	
	public PecaXadrez trocaPecaPromovida(String tipo) {
		if (promovido == null) {
			throw new IllegalStateException("Nao ha peca para ser promovida!");
		}
		if(!tipo.contentEquals("B") && !tipo.contentEquals("C") && !tipo.contentEquals("T") && !tipo.contentEquals("Q")) {
			throw new InvalidParameterException("Este não é um tipo válido para a promoção");
		}
		
		Posicao pos = promovido.getPosicaoXadrez().cvPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if (tipo.equals("T")) return new Torre(tabuleiro, cor);
		return new Rainha(tabuleiro, cor);
			
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
		//movimento especial roque rei
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.lugarPeca(torre, destinoT);
			torre.admov();	
		}
		//movimento especial roque rainha
				if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
					Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
					Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
					PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
					tabuleiro.lugarPeca(torre, destinoT);
					torre.admov();	
				}
		
		//#movimento especial enpasant
				if (p instanceof Peao) {
					if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
						Posicao posicaoPeao;
						if(p.getCor() == Cor.BRANCO) {
							posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
						}
						else {
							posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
						}
						pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
						pecasCapturadas.add(pecaCapturada);
						pecasNoTabuleiro.remove(pecaCapturada);
					}
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
		//movimento especial roque rei
				if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
					Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
					Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
					PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
					tabuleiro.lugarPeca(torre, origemT);
					torre.submov();	
				}
				//movimento especial roque rainha
						if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
							Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
							Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
							PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
							tabuleiro.lugarPeca(torre, origemT);
							torre.submov();	
						}
						//#movimento especial enpasant
						if (p instanceof Peao) {
							if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVuneravel) {
								PecaXadrez peao = (PecaXadrez)tabuleiro.removerPeca(destino);
								Posicao posicaoPeao;
								if(p.getCor() == Cor.BRANCO) {
									posicaoPeao = new Posicao(3, destino.getColuna());
								}
								else {
									posicaoPeao = new Posicao(4, destino.getColuna());
								}
								tabuleiro.lugarPeca(peao, posicaoPeao);
		
							}
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
		lugarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));
	

		lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}

}
