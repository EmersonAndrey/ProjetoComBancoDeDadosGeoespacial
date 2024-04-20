package view;

import java.util.List;
import java.util.Scanner;

import controller.LocalizacaoController;
import model.Localizacao;

public class main {

	public static void main(String[] args) {	
		Scanner entrada = new Scanner(System.in);
		
		boolean sair = false;
		while(!sair) {
			System.out.println("Escola uma opcao");
			System.out.println("[1] Cadastrar");
			System.out.println("[2] Listar");
			System.out.println("[3] Atualizar");
			System.out.println("[4] Remover");
			System.out.println("[5] Sair");
			
			System.out.println("Escolhe uma opcao");
			String opcao = entrada.nextLine();
			
			List<Localizacao> todosPontos = LocalizacaoController.getInstance().buscarPontos();
			
			switch(opcao) {
				case "1":
					break;
				case "2":
					
					for(int i = 0; i < todosPontos.size(); i++) {
						System.out.println("["+ (i+1) +"] " + todosPontos.get(i).getNome());
					}
					break;
				case "3":
					break;
				case "4":
					System.out.println("Escolhe uma opcao para remover");
					String opcaoRemover = entrada.nextLine();
					
					break;
					
				case "5":
					sair = true;
					break;
				default:
					System.out.println("Escolhe uma opcao bixiga");
				
			}
				
			
		}
		
		
	}

}
