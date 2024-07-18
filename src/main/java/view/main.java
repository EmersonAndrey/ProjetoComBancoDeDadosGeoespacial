package view;

import java.util.Scanner;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;

import controller.FuncoesMenuController;
import model.Localizacao;

public class main {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		//CADASTRAR: Pousada Bela Vista, -7.889737579924742, -37.109802189537135

		boolean sair = false;
		while (!sair) {
			System.out.println("MENU");
			System.out.println("[1] Cadastrar");
			System.out.println("[2] Listar");
			System.out.println("[3] Atualizar");
			System.out.println("[4] Remover");
			System.out.println("[5] Calcular distância");
			System.out.println("[6] Sair");

			System.out.print("Escolhe uma opcao: ");
			String opcao = entrada.nextLine();

			switch (opcao) {
			case "1":
				System.out.println("Digite um nome, latitude e longitude para cadastrar um ponto");
				String nome = entrada.nextLine();
				double latitude = Double.parseDouble(entrada.nextLine());
				double longitude = Double.parseDouble(entrada.nextLine());

				if (FuncoesMenuController.getInstance().cadastrarPonto(nome, latitude, longitude)) {
					System.out.println("Ponto cadastrado com sucesso");
				}
				break;
				

			case "2":
				FuncoesMenuController.getInstance().listaTodosOsPontosCad();
				break;
				

			case "3":
				FuncoesMenuController.getInstance().listaTodosOsPontosCad();
				System.out.print("Digite o id do ponto que deseja atualizar: ");
				int idAtualizar = Integer.parseInt(entrada.nextLine());

				Localizacao localizacao = null;

				try {
					localizacao = FuncoesMenuController.getInstance().buscarLocalizacaoPorId(idAtualizar);

				} catch (Exception e) {

				}

				System.out.println("Digite um nome, latitude e longitude para atualizar um ponto");
				String nomeAtualizar = entrada.nextLine();
				double latitudeAtualizar = Double.parseDouble(entrada.nextLine());
				double longitudeAtualizar = Double.parseDouble(entrada.nextLine());

				localizacao.setNome(nomeAtualizar);

				try {
					localizacao.setCoordenadas((Point) new WKTReader()
							.read("POINT (" + latitudeAtualizar + " " + longitudeAtualizar + ")"));

					FuncoesMenuController.getInstance().atualizarPonto(localizacao);

				} catch (Exception e) {
				}

				System.out.println("Ponto atualizado com sucesso");
				break;
				

			case "4":
				FuncoesMenuController.getInstance().listaTodosOsPontosCad();
				System.out.print("Escolhe uma opcao para remover: ");
				int opcaoRemover = Integer.parseInt(entrada.nextLine());

				try {
					FuncoesMenuController.getInstance().removerPonto(opcaoRemover);

				} catch (Exception e) {
					System.out.println("Não foi possível remover o ponto");
				}

				break;
				

			case "5":
				System.out.println("Como deseja calcular a distancia? (Por raio ou Linear): ");
				System.out.println("[1] Linear");
				System.out.println("[2] Raio");
				int formaCalcularDistancia = Integer.parseInt(entrada.nextLine());
				
				System.out.println("Informe sua localizacao atual(latitude e longitude):");
				double latitudeFixo = Double.parseDouble(entrada.nextLine());
				double longitudeFixo = Double.parseDouble(entrada.nextLine());

				if (formaCalcularDistancia == 1) {
					try {
						FuncoesMenuController.getInstance().calcularDistanciaEntrePontos(latitudeFixo, longitudeFixo);

					} catch (Exception e) {}
					
				}else {
					FuncoesMenuController.getInstance().calcularDistanciaEntrePontosPorRaio(latitudeFixo, longitudeFixo);
				}

				break;

				
			case "6":
				sair = true;
				break;

			default:
				System.out.println("Escolha uma opção válida");

			}

		}

	}

}
