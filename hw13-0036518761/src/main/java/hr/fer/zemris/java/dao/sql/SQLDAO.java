package hr.fer.zemris.java.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.Anketa;
import hr.fer.zemris.java.model.AnketaOpcija;

public class SQLDAO implements DAO {

	@Override
	public List<Anketa> dohvatiAnkete() throws DAOException {
		List<Anketa> ankete = new ArrayList<>();
		Connection c = SQLConnectionProvider.getConnection();
		try(PreparedStatement pst = c.prepareStatement("select id, title, message from Polls order by id")){
			try(ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					Anketa anketa = new Anketa();
					anketa.setId(rs.getLong(1));
					anketa.setTitle(rs.getString(2));
					anketa.setMessage(rs.getString(3));
					ankete.add(anketa);
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste anketa.", ex);
		}
		return ankete;
	}

	@Override
	public Anketa dohvatiAnketu(long id) throws DAOException {
		Anketa anketa = null;
		Connection con = SQLConnectionProvider.getConnection();
		try(PreparedStatement pst = con.prepareStatement("select id, title, message from Polls where id=?")) {
			pst.setLong(1, id);
			try(ResultSet rs = pst.executeQuery()) {
				if(rs!=null && rs.next()) {
					anketa = new Anketa();
					anketa.setId(id);
					anketa.setMessage(rs.getString(3));
					anketa.setTitle(rs.getString(2));
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return anketa;
	}

	@Override
	public List<AnketaOpcija> dohvatiOpcije(long pollId) throws DAOException {
		List<AnketaOpcija> opcije = new ArrayList<>();
		AnketaOpcija a = null;
		Connection con = SQLConnectionProvider.getConnection();
		
		try(PreparedStatement pst = con.prepareStatement(
				"select id, optionTitle, optionLink, pollId, votesCount from PollOptions where pollId=? "
				+ "order by votesCount desc")) {
			pst.setLong(1, pollId);
			try(ResultSet rs = pst.executeQuery()) {
				while(rs!=null && rs.next()) {
					a = new AnketaOpcija();
					a.setId(rs.getLong(1));
					a.setOptionTitle(rs.getString(2));
					a.setOptionLink(rs.getString(3));
					a.setPollId(rs.getLong(4));
					a.setVotesCount(rs.getLong(5));
					opcije.add(a);
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return opcije;
	}

	@Override
	public void dodajGlas(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try(PreparedStatement pst = con.prepareStatement("select votesCount from PollOptions where id=?")) {
			pst.setLong(1, id);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				long votesCount = rs.getLong(1) + 1;
				try(PreparedStatement pst2 = con.prepareStatement("UPDATE PollOptions "
						+ "SET votesCount = "+ votesCount + " "
						+ "where id=?")) {
					pst2.setLong(1, id);
					pst2.executeUpdate();
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
	}

}