package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

public class Program {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>();
		
		while(!partidaXadrez.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printPartida(partidaXadrez, capturada);
				System.out.println();
				System.out.println("Digite a casa de origem");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.clearScreen();
				UI.printTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);
				System.out.println();
				System.out.println("Digite a casa de destino");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				PecaXadrez pecaCapturada = partidaXadrez.movimentoPeca(origem, destino);
				if(pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
				
				if (partidaXadrez.getPromovido() != null) {
					System.out.print("Digite a letra correspondente a pe�a par ser promovida(T/C/B/Q):");
					String tipo = sc.nextLine();
					partidaXadrez.trocaPecaPromovida(tipo);
					
				}
			}
			catch(XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printPartida(partidaXadrez, capturada);

	}

}
