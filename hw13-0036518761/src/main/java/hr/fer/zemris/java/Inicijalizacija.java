package hr.fer.zemris.java;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(
					new File(sce.getServletContext().getRealPath(
							"WEB-INF/dbsettings.properties"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String dbName = p.getProperty("name");
		String host = p.getProperty("host");
		String port = p.getProperty("port");
		String user = p.getProperty("user");
		String password = p.getProperty("password");
		
		String connectionURL = "jdbc:derby://"+host+":"+ port +"/"+ dbName + ";user="+user+";password="+password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.client.ClientAutoloadedDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		PreparedStatement pst = null;
		Connection c = null;
		
		try {
			c = cpds.getConnection();
			pst = c.prepareStatement("""
					CREATE TABLE Polls (
						id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
						title VARCHAR(150) NOT NULL,
						message CLOB(2048) NOT NULL
					)
					""");
			pst.executeUpdate();
		} catch(SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				e.printStackTrace();
			}
		}
		
		try {
			pst = c.prepareStatement("""
					CREATE TABLE PollOptions(
						id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
						optionTitle VARCHAR(100) NOT NULL,
						optionLink VARCHAR(150) NOT NULL,
						pollID BIGINT,
						votesCount BIGINT,
						FOREIGN KEY (pollID) REFERENCES Polls(id)
					)
					""");
			pst.executeUpdate();
		} catch(SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				e.printStackTrace();
			}
		}
		
		try {
			ResultSet rs = (ResultSet) c.prepareStatement("SELECT * from Polls").executeQuery();
			if(!rs.next()) {//nemamo nijednu anketu u bazi
				ResultSet keys = null;
				long firstKey, secondKey;

				pst = c.prepareStatement("""
						INSERT INTO Polls (title, message) VALUES
						('Glasanje za omiljeni bend:',
						'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!')
						""", Statement.RETURN_GENERATED_KEYS);
				pst.executeUpdate();
				keys = pst.getGeneratedKeys();
				
				if(keys.next()) {
					firstKey = keys.getLong(1);
					StringBuilder sb = new StringBuilder();
					sb.append("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES ");
					sb.append("('The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg' , "+firstKey+", 0), ");
					sb.append("('The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU', "+firstKey+", 0), ");
					sb.append("('The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU', "+firstKey+", 0), ");
					sb.append("('The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds', "+firstKey+", 0), ");
					sb.append("('The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs', "+firstKey+", 0), ");
					sb.append("('The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8', "+firstKey+", 0), ");
					sb.append("('The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk', "+firstKey+", 0)");

					pst = c.prepareStatement(sb.toString());
					pst.executeUpdate();
				}
				
				pst = c.prepareStatement("""
						INSERT INTO Polls (title, message) VALUES
						('Glasanje za omiljeni tim:',
						'Od sljedećih timova, koji Vam je tim najdraži? Kliknite na link kako biste glasali!')
						""", Statement.RETURN_GENERATED_KEYS);
				pst.executeUpdate();
				keys = pst.getGeneratedKeys();
				if(keys.next()) {
					secondKey = keys.getLong(1);
					StringBuilder sb = new StringBuilder();
					sb.append("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES ");
					sb.append("('Team Secret', 'https://teamsecret.gg', "+secondKey+", 0), ");
					sb.append("('EG', 'https://evilgeniuses.gg', "+secondKey+", 0), ");
					sb.append("('OG', 'https://ogs.gg', "+secondKey+", 0), ");
					sb.append("('Team Liquid', 'https://www.teamliquid.com', "+secondKey+", 0), ");
					sb.append("('Alliance', 'https://thealliance.gg', "+secondKey+", 0), ");
					sb.append("('Team Nigma', 'https://teamnigma.com', "+secondKey+", 0), ");
					sb.append("('PSG.LGD', 'https://psg-esports.com/home', "+secondKey+", 0)");

					pst = c.prepareStatement(sb.toString());
					pst.executeUpdate();
				}
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
