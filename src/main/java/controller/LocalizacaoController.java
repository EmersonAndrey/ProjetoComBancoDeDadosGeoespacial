package controller;

import java.util.List;

import javax.persistence.EntityManager;

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
	
	public List<Localizacao> buscarPontos(){
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

		for (Localizacao ponto : this.localizacaoDAO.buscarTodosOsPontos()) {
			String[] pontoCadastrado = retornaVetorCordenadas(ponto.getCoordenadas());

			double distancia = calcularDistancia(latitude, longitude, Double.parseDouble(pontoCadastrado[2]),
					Double.parseDouble(pontoCadastrado[3]));
			System.out.println("Distância entre o ponto fixo e o ponto " + ponto.getNome() + ": " + distancia + "Km");
		}

	}

	public static double calcularDistancia(double x1, double y1, double x2, double y2) {
		final int RAIO_TERRA = 6371;

		double x01 = Math.toRadians(x1);
		double y01 = Math.toRadians(y1);
		double x02 = Math.toRadians(x2);
		double y02 = Math.toRadians(y2);

		double diferencaX = x02 - x01;
		double diferencaY = y02 - y01;

		double a = Math.pow(Math.sin(diferencaX / 2), 2)
				+ Math.cos(x01) * Math.cos(x02) * Math.pow(Math.sin(diferencaY / 2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distanciaEmQuilometros = RAIO_TERRA * c;

		return Math.round(distanciaEmQuilometros * 100.0) / 100.0;
	}

	public static String[] retornaVetorCordenadas(Geometry coordenada) {
		return coordenada.toString().split("[\\s()]");
	}
}
