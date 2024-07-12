package controller;

import java.util.List;

import model.Localizacao;

public class FuncoesMenuController {

	private static volatile FuncoesMenuController instance;
	
	private FuncoesMenuController() {
		
	}
	
	public static FuncoesMenuController getInstance() {
		if (instance == null) {
			synchronized (FuncoesMenuController.class) {
				if (instance == null) {
					instance = new FuncoesMenuController();
				}
			}
		}
		return instance;
	}
	
	
	public void listaTodosOsPontosCad() {
		List<Localizacao> todosPontos = LocalizacaoController.getInstance().buscarPontos();
		
		System.out.println("Lista dos pontos cadastrados");
		for(int i = 0; i < todosPontos.size(); i++) {
			System.out.println("["+ (todosPontos.get(i).getId()) +"] " + todosPontos.get(i).getNome());
		}
		System.out.println();
	}
	
	public boolean cadastrarPonto(String nome, double latitude, double longitude) {
		return LocalizacaoController.getInstance().cadastrarPonto(nome, longitude, latitude);
	}
	
	public boolean removerPonto(int idLocalizacao) throws Exception {
		return LocalizacaoController.getInstance().removerPonto(idLocalizacao);
	}
	
	public boolean atualizarPonto(Localizacao entidade) throws Exception {
		return LocalizacaoController.getInstance().atualizarPonto(entidade);
	}
	
	public Localizacao buscarLocalizacaoPorId(int idLocalizacao) throws Exception {
		return LocalizacaoController.getInstance().buscarLocalizacaoPorId(idLocalizacao);
	}
	
	public void calcularDistanciaEntrePontos(double latitude, double longitude) throws Exception {
		LocalizacaoController.getInstance().calcularDistanciaEntrePontos(longitude, latitude);
	}
	
}
