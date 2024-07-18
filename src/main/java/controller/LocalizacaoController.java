package controller;

import java.text.DecimalFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.locationtech.jts.geom.Geometry;

import connection.ConnectionFactory;
import dao.LocalizacaoDAO;
import model.Localizacao;

public class LocalizacaoController {

	private static volatile LocalizacaoController instance;
	private LocalizacaoDAO localizacaoDAO = LocalizacaoDAO.getInstance();

	private LocalizacaoController() {

	}

	public static LocalizacaoController getInstance() {
		if (instance == null) {
			synchronized (LocalizacaoController.class) {
				if (instance == null) {
					instance = new LocalizacaoController();
				}
			}
		}
		return instance;
	}

	public List<Localizacao> buscarPontos() {
		return this.localizacaoDAO.buscarTodosOsPontos();
	}

	public boolean cadastrarPonto(String nome, double latitude, double longitude) {
		return this.localizacaoDAO.cadastrarPonto(nome, latitude, longitude);
	}

	public boolean removerPonto(int idLocalizacao) throws Exception {
		return this.localizacaoDAO.removerLocalizacao(idLocalizacao);
	}

	public boolean atualizarPonto(Localizacao entidade) throws Exception {
		return this.localizacaoDAO.atualizarLocalizacao(entidade);
	}

	public Localizacao buscarLocalizacaoPorId(int idLocal) throws Exception {
		return this.localizacaoDAO.buscarLocalizacao(idLocal);
	}

	public void calcularDistanciaEntrePontos(double latitude, double longitude) throws Exception {
		this.localizacaoDAO.calcularDistanciaEntrePontos(latitude, longitude);
	}
	
	public void calcularDistanciaEntrePontosPorRaio(double longitude, double latitude) {
		this.localizacaoDAO.calcularDistanciaEntrePontosPorRaio(latitude, longitude);
	}

}
