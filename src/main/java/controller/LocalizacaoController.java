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

	public void calcularDistanciaEntrePontos(int idLocal) throws Exception {
		EntityManager em = new ConnectionFactory().getConnection();

		String[] pontoFixo = retornaVetorCordenadas(this.localizacaoDAO.buscarLocalizacao(idLocal).getCoordenadas());

		for (Localizacao ponto : this.localizacaoDAO.buscarTodosOsPontos()) {
			String[] pontoCadastrado = retornaVetorCordenadas(ponto.getCoordenadas());

			double distancia = calcularDistancia(Double.parseDouble(pontoFixo[2]),
					Double.parseDouble(pontoFixo[3]), Double.parseDouble(pontoCadastrado[2]),
					Double.parseDouble(pontoCadastrado[3]));
			System.out.println("Distância entre o ponto fixo e o ponto com ID " + ponto.getNome() + ": " + distancia + "Km");
		}

	}

	public static double calcularDistancia(double x1, double y1, double x2, double y2) {
		final int RAIO_TERRA = 6371;

		double x1Rad = Math.toRadians(x1);
		double y1Rad = Math.toRadians(y1);
		double x2Rad = Math.toRadians(x2);
		double y2Rad = Math.toRadians(y2);

		double diffX = x2Rad - x1Rad;
		double diffY = y2Rad - y1Rad;

		double a = Math.pow(Math.sin(diffX / 2), 2)
				+ Math.cos(x1Rad) * Math.cos(x2Rad) * Math.pow(Math.sin(diffY / 2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distanciaEmQuilometros = RAIO_TERRA * c;

		return Math.round(distanciaEmQuilometros * 100.0) / 100.0;
	}

	public static String[] retornaVetorCordenadas(Geometry coordenada) {
		return coordenada.toString().split("[\\s()]");
	}
}
