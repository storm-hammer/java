package hr.fer.zemris.java.dao;

import java.util.List;

import hr.fer.zemris.java.model.Anketa;
import hr.fer.zemris.java.model.AnketaOpcija;

public interface DAO {


	public List<Anketa> dohvatiAnkete() throws DAOException;
	
	public Anketa dohvatiAnketu(long id) throws DAOException;
	
	public List<AnketaOpcija> dohvatiOpcije(long id) throws DAOException;
	
	public void dodajGlas(long id) throws DAOException;
}