
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;


public class SQLHandler {
	
	private Connection connection = null;
	private java.sql.Statement stmt = null;
	private String url = "jdbc:mariadb://localhost:3306/vaader";
	private String user = "root";
	private String psw = "raspberry";
	
	private ResultSet rs = null;
	
	private final DecimalFormat decfor = new DecimalFormat("0.00");  
	
public SQLHandler() {
	try {
		
		connection = DriverManager.getConnection(url, user, psw);
		stmt = connection.createStatement();
		
		System.out.println("connected!");
		
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
	
public void closeConnection(){
	try {
		
		if (!(rs==null)) {
			rs.close();
		}
		
		stmt.close();
		connection.close();
		System.out.println("closed!");
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
}

	public List<KalenderData> getKalenderData(){
		
		List<KalenderData> list = new ArrayList<>();
		try {
			
			String sql;
			sql = "SELECT * FROM Kalender;";
			
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Date date = rs.getDate("data_date");
				int dagtyp = rs.getInt("dagtyp");
				
				
				list.add(new KalenderData(date, dagtyp));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.unmodifiableList(list);
		
	}


	public void insertDagtData(String typ, String timme, String tmin, String tmax){
		
		try {
			
			String sql;
			sql = "INSERT INTO Dagtyp (dagtyp,timme,Tmin,Tmax) VALUES (" + typ+ "," + timme + "," + tmin + "," + tmax + ");";
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} 
	
	public void deleteDagtData(String dagtyp, String timme) {
		try {

			String sql;
			sql = createDeleteStatement(dagtyp, timme);
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private String createDeleteStatement(String dagtyp, String timme) {
		StringBuilder str = new StringBuilder();
		str.append("DELETE FROM Dagtyp \n"
				+ "where dagtyp=");
		str.append(dagtyp);
		str.append(" and timme=");
		str.append(timme);
		str.append(";");
		return str.toString();
	}
	
	public void updateDagtData(String dagtyp, String timme, String tmin, String tmax) {
		try {

			String sql;
			sql = createUpdateStatement(dagtyp, timme, tmin, tmax);
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String createUpdateStatement(String dagtyp, String timme, String tmin, String tmax) {
		StringBuilder str = new StringBuilder();
		str.append("UPDATE Dagtyp \n"
				+ "set tmin=");
		str.append(tmin);
		str.append(",tmax=");
		str.append(tmax + "\n");
		str.append("where dagtyp=");
		str.append(dagtyp);
		str.append(" and timme=");
		str.append(timme);
		str.append(";");
		
		return str.toString();
		
	}
	
	public boolean ifKeyExists(String dagtyp, String timme) {
		boolean exists = false;
		
		try {

			String sql = createExistsStatement(dagtyp, timme);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			int timmeInt = Integer.parseInt( timme );
			
			while(rs.next()) {

				String dagtypen = rs.getString("dagtyp");
				
				int timmedag = rs.getInt("timme");
				
				if (dagtyp.equals(dagtypen) && timmeInt == timmedag) {
					exists = true;
				}
				
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return exists;
		
		
	}
	
	private String createExistsStatement(String key, String timme) {
		StringBuilder str = new StringBuilder();
		str.append("SELECT dagtyp,timme\n"
				+ "FROM Dagtyp \n"
				+ "where dagtyp=");
		str.append(key);
		str.append(" and timme=");
		str.append(timme);
		str.append(";");
		return str.toString();
	}
	
	
	
	public List<DagtData> getDagtData(){
		
		List<DagtData> list = new ArrayList<>();
		try {
			
			String sql;
			sql = "SELECT dagtyp,timme,Tmin,Tmax FROM Dagtyp;";
			
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int typ = rs.getInt("dagtyp");
				int timme = rs.getInt("timme"); 
				int tmin = rs.getInt("tmin");
				int tmax = rs.getInt("tmax");
				
				
				list.add(new DagtData(typ, timme, tmin, tmax));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.unmodifiableList(list);
		
	}
	
	


	public List<ResData> getResultatData(String fromDate, String hour) {
		List<ResData> list = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String bertidStr = "";
		try {
			
			String sql;
			sql = createResultatDataStatement(fromDate, hour);
			
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Timestamp bertid = rs.getTimestamp("bertid");
				bertidStr = formatter.format(bertid);
				String typ = rs.getString("typ");
				int altid = rs.getInt("alt_id");
				Date date = rs.getDate("data_date");
				int timme = rs.getInt("Rtimme"); 
				double temp = rs.getDouble("Rtemp");
				
				
				list.add(new ResData(bertidStr, typ, altid, date, timme, temp));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Collections.unmodifiableList(list); 
	}
	
	private String createResultatDataStatement(String date, String hour) {
		StringBuilder str = new StringBuilder();
		str.append("SELECT bertid,typ,alt_id,data_date,Rtimme,Rtemp FROM resultat WHERE data_date>="
				+ date + " and Rtimme>=" + hour +";");
		return str.toString();
	}
	
	
	
	
	public List<Data> getBetweenDatesData(String dateFrom, String dateTo){
		
		List<Data> list = new ArrayList<>(); 
		
		try {
			
			String sql;
			sql = createBetweenDatesStatement(dateFrom, dateTo);
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Date date = rs.getDate("data_date");
				int timme = rs.getInt("timme"); 
				double kost = rs.getDouble("NORDPpris");
				int tmin = rs.getInt("Tmin");
				int tmax = rs.getInt("Tmax");
				double tempupp = rs.getDouble("tempUpp");
				double tempned = rs.getDouble("tempNed");
				
				list.add(new Data(date, timme, kost, tmin, tmax, tempupp, tempned));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Collections.unmodifiableList(list); 
		
	}
	
	public List<SimData> getSimData(String date, String timme, String id, String temp) {
	
		List<SimData> list = new ArrayList<>(); 
		
		try {
			
			String sql;
			sql = createSimDataStatement(date, timme, id);
			//System.out.println(sql);
			
			double paaAlfa = getpaaAlfa();
			double avBeta = getavBeta();
			
			rs = stmt.executeQuery(sql);
			
			
			double kostnadTotal = 0;
			double temperatur = Double.parseDouble(temp);
			double temperaturTotal = temperatur;
			
			while(rs.next()) {
				
				int ordning = rs.getInt("ordning");
				Date dateS = rs.getDate("data_date");
				int timmeS = rs.getInt("timme"); 
				int min = rs.getInt("min");
				double kost = rs.getDouble("hnordppris");
				int tmin = rs.getInt("tmin");
				int tmax = rs.getInt("tmax");
				//double tempupp = rs.getDouble("tempupp");
				//double tempned = rs.getDouble("tempned");
				int paav = rs.getInt("paav");
				double SMHItemp = rs.getDouble("SMHItemp");	
				double tempupp = (paaAlfa - (temperaturTotal-SMHItemp)*avBeta)*0.5;
				double tempned = (temperaturTotal-SMHItemp)*avBeta*0.5; 
				
				if (paav == 1) {
					kostnadTotal += kost;
					temperaturTotal += tempupp;
					
				} 
				
				if (paav == 0) {
					temperaturTotal -= tempned;
				}
				
				/*kost = Double.parseDouble(decfor.format(kost));
				tempupp = Double.parseDouble(decfor.format(tempupp));
				tempned = Double.parseDouble(decfor.format(tempned));
				temperaturTotal= Double.parseDouble(decfor.format(temperaturTotal));*/
				
				list.add(new SimData(dateS, timmeS, kost, tmin, tmax, 
						tempupp, tempned, ordning, min, paav, kostnadTotal, temperaturTotal));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Collections.unmodifiableList(list); 
		
	}
	
	private double getavBeta() {
		double avBeta = 0;
		try {
			
			String sql;
			sql = "SELECT avBeta from Parametrar;";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				avBeta = rs.getDouble("avBeta");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avBeta;
	}
	
	private double getpaaAlfa() {
		double paaAlfa = 0;
		try {
			
			String sql;
			sql = "SELECT paaAlfa from Parametrar;";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				paaAlfa = rs.getDouble("paaAlfa");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return paaAlfa;
	}
	
	private String createSimDataStatement(String date, String timme, String id) {
		StringBuilder str = new StringBuilder();
		//String dateTimme = date + timme;
		String toDate = getSixHoursAddedDate(date + timme);
		String toDateNoHour = toDate.substring(0,8);
		
		str.append("SELECT x.nrtimme as ordning,f.data_date,g.timme,x.min,c.NORDPpris*0.5 as"
				+ " hNORDPpris,g.Tmin,g.Tmax,paav,e.SMHItemp\n"
				+ "FROM ( Kalender f)   \n"
				+ "JOIN (Dagtyp g,NORDP c,SMHI e, connect x,alternativ t)   \n"
				+ "ON (f.dagtyp=g.dagtyp and c.data_date=f.data_date and "
				+ "g.timme = c.NORDPtimme  and c.data_date=e.data_date and "
				+ "g.timme = e.SMHItimme and g.timme=x.timme and x.nrtimme=t.timme)   \n"
				+ "where  f.data_date*100+g.timme>=");
		str.append(date + timme + " and  f.data_date*100+g.timme<=" + toDate);
		str.append("  and x.starttimme=" + timme + " and t.altnr_id = " + id);
		str.append(" and (f.data_date=" + date + " or f.data_date=" + toDateNoHour + ")\n"
				+ "ORDER BY f.data_date,g.timme,x.min;");	
		return str.toString();
	}
	
	private String getSixHoursAddedDate(String date) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
		 String strDate = "";
		
		 try {
			Date dateAsDate = formatter.parse(date);
			
			int HOURS = 6; 
			
			Calendar calendar2 = Calendar.getInstance();
		    calendar2.setTime(dateAsDate);
		    calendar2.add(Calendar.HOUR_OF_DAY, HOURS);
		    
		    Date newDate = calendar2.getTime();
		    strDate = formatter.format(newDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		 
		return strDate;
	}
	

	private String createBetweenDatesStatement(String dateFrom, String dateTo) {
		StringBuilder str = new StringBuilder();
		
		str.append("SELECT f.data_date,g.timme,c.NORDPpris,g.Tmin,g.Tmax,(g.Tmin-e.SMHItemp)*d.avBeta as TempNed,d.paaAlfa-(g.Tmin-e.SMHItemp)*d.avBeta as TempUpp\n"
				+ "FROM (Parametrar d, Kalender f)  \n"
				+ "JOIN (Dagtyp g,NORDP c,SMHI e)  \n"
				+ "ON (f.dagtyp=g.dagtyp and c.data_date=f.data_date and g.timme = c.NORDPtimme  and c.data_date=e.data_date and g.timme = e.SMHItimme )  \n"
				+ "where  f.data_date>=");	
		str.append(dateFrom);
		str.append(" and  f.data_date<=");
		str.append(dateTo + "\n");
		str.append("ORDER BY f.data_date,g.timme;");
		return str.toString();
	
		}

}
