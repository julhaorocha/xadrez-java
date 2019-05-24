package application;

import tabuleiro.Posicao;
import tabuleiro.TabuleiroJogo;
import xadrez.PartidaXadrez;

public class Program {

	public static void main(String[] args) {
	
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		UI.printTabuleiro(partidaXadrez.getPecas());
		
				

	}

}
