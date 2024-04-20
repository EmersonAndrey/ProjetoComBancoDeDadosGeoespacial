package dao;

import java.util.List;

import model.Localizacao;

public interface LocalizacaoDAOInterface {

	boolean cadastrarPonto(String nome, double latitude, double longitude);
	Localizacao buscarLocalizacao(int idLocal) throws Exception;
	List<Localizacao> buscarTodosOsPontos();
	boolean removerLocalizacao(int idLocalizacao) throws Exception;
	boolean atualizarLocalizacao(Localizacao entidade) throws Exception;
	
}

