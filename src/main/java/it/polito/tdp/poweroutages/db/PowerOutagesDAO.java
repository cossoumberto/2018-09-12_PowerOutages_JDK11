package it.polito.tdp.poweroutages.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.polito.tdp.poweroutages.model.Coppia;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutagesDAO {
	
	public List<PowerOutage> listAllPO(Map<Integer, Nerc> idMap) {
		String sql = "SELECT id, nerc_id, date_event_began, date_event_finished FROM poweroutages";
		List<PowerOutage> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next())
				list.add(new PowerOutage(res.getInt("id"), idMap.get(res.getInt("nerc_id")), 
						res.getTimestamp("date_event_began").toLocalDateTime(), res.getTimestamp("date_event_finished").toLocalDateTime()));
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void loadAllNercs(Map<Integer, Nerc> idMap) {
		String sql = "SELECT id, value FROM nerc";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				idMap.put(n.getId(), n);
			}
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Coppia> getCoppie(Map <Integer, Nerc> idMap) {
		String sql = "SELECT DISTINCT P1.nerc_id AS N1, P2.nerc_id AS N2, COUNT(DISTINCT MONTH(P1.date_event_began),YEAR(P1.date_event_began)) AS C " +
						"FROM poweroutages AS P1, poweroutages AS P2, nercrelations AS N " +
						"WHERE P1.nerc_id=N.nerc_one AND P2.nerc_id=N.nerc_two AND MONTH(P1.date_event_began)=MONTH(P2.date_event_began) " + 
						"AND YEAR(P1.date_event_began)=YEAR(P2.date_event_began) " +
						"GROUP BY P1.nerc_id, P2.nerc_id";
		List<Coppia> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Coppia c = new Coppia(idMap.get(res.getInt("N1")), idMap.get(res.getInt("N2")), res.getInt("C"));
				list.add(c);
			}
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
