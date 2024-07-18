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
		EntityManager em = new ConnectionFactory().getConnection();

//		String queryString = "SELECT l.nome, " + "ST_Distance(l.coordenadas, "
//				+ "ST_SetSRID(ST_MakePoint(?, ?), 4326)) AS distance_meters " + "FROM localizacoes l";
		
		String queryString = "SELECT l.nome, " +
                "ST_Distance(" +
                "ST_Transform(l.coordenadas, 3857), " +
                "ST_Transform(ST_SetSRID(ST_MakePoint(?, ?), 4326), 3857))" +
                "FROM localizacoes l";

		Query query = em.createNativeQuery(queryString);
		query.setParameter(1, latitude);
		query.setParameter(2, longitude);

		List<Localizacao[]> results = query.getResultList();

		DecimalFormat metrosFormatados = new DecimalFormat("#.##");

		for (Object[] result : results) {
			String nome = (String) result[0];
			Double distancia = ((Number) result[1]).doubleValue() / 1000;
			String distanciaFormatada = metrosFormatados.format(distancia);
			System.out.println("Local: " + nome + ", Distancia: " + distanciaFormatada + "Km");
		}

		em.close();

	}
	


	public void calcularDistanciaEntrePontosPorRaio(double longitude, double latitude) {
		EntityManager em = new ConnectionFactory().getConnection();
		
		String querieString = "SELECT nome, ST_AsText(coordenadas) FROM localizacoes WHERE ST_DWithin(coordenadas, ST_SetSRID(ST_MakePoint(?, ?), 4326), 500)";
		
		Query query = em.createNativeQuery(querieString);
		query.setParameter(1, longitude);
		query.setParameter(2, latitude);
		
		List<Localizacao[]> results = query.getResultList();

		for (Object[] result : results) {
			String nome = (String) result[0];
			String ponto = (String)(result[1]);
			System.out.println("Local: " + nome + ", Coordenadas: " + ponto);
		}

		em.close();
		
	}

}
