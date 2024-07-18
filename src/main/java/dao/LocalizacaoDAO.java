package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import connection.ConnectionFactory;
import model.Localizacao;

public class LocalizacaoDAO implements LocalizacaoDAOInterface{

	private static volatile LocalizacaoDAO instance;
	
	 private static final int SRID = 4326; 
	
	private LocalizacaoDAO() {
		
	}
	
	public static LocalizacaoDAO getInstance() {
		if (instance == null) {
			synchronized (LocalizacaoDAO.class) {
				if (instance == null) {
					instance = new LocalizacaoDAO();
				}
			}
		}
		return instance;
	}
	
	@Override
	public boolean cadastrarPonto(String nome, double latitude, double longitude) {
		EntityManager em = new ConnectionFactory().getConnection();

		Localizacao localizacao = new Localizacao();
		localizacao.setNome(nome);
		
		try {
			GeometryFactory geometryFactory = new GeometryFactory();

            Point point = (Point) new WKTReader(geometryFactory).read("POINT (" + latitude + " " + longitude + ")");
            point.setSRID(SRID);
            localizacao.setCoordenadas(point);
            
			em.getTransaction().begin();
			em.persist(localizacao);
			em.getTransaction().commit();
			em.close();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	@Override
	public Localizacao buscarLocalizacao(int idLocal) throws Exception {
		EntityManager em = new ConnectionFactory().getConnection();
		
		try {
			
			return em.find(Localizacao.class, idLocal);
			
		} catch (Exception e) {
			throw e;
			
		}finally {
			em.close();
		} 
		
	}
	
	@Override
	public List<Localizacao> buscarTodosOsPontos(){
		EntityManager em = new ConnectionFactory().getConnection();
		TypedQuery<Localizacao> query = em.createQuery("SELECT l FROM Localizacao l", Localizacao.class);
        return query.getResultList();
	}

	@Override
	public boolean removerLocalizacao(int idLocalizacao) throws Exception {
		EntityManager em = new ConnectionFactory().getConnection();
		
		Localizacao localizacaoEncontrada = buscarLocalizacao(idLocalizacao);
		
		if(localizacaoEncontrada != null) {
			em.getTransaction().begin();
			em.remove(em.contains(localizacaoEncontrada) ? localizacaoEncontrada : em.merge(localizacaoEncontrada));
			em.getTransaction().commit();
			em.close();
			
			return true;
			
		}		
		return false;
	}

	@Override
	public boolean atualizarLocalizacao(Localizacao entidade) throws Exception{
		EntityManager em = new ConnectionFactory().getConnection();
		
		em.getTransaction().begin();
		em.merge(entidade);
		em.getTransaction().commit();
		em.close();
		
		return true;
	}
	
	
	

	
}
