package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "localizacoes")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    //Define como um ponto geografico no banco de dados
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point coordenadas;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Geometry getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Point coordenadas) {
		this.coordenadas = coordenadas;
	}


}
