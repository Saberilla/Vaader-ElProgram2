
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Calendar;
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

	private void open() {
		
		try {
			
			connection = DriverManager.getConnection(url, user, psw);
			stmt = connection.createStatement();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("open");
	}
	
	private void close() {
		try {
			
			rs.close();
			stmt.close();
			connection.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public List<DefaulterData> getDefaulterData(){
		
		open();
		
		List<DefaulterData> list = new ArrayList<>();
		try {
			
			String sql;
			sql = "SELECT xdefault,typ,varde FROM Defaulter;";
			
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String defaulter = rs.getString("xdefault");
				String typ = rs.getString("typ");
				double varde = rs.getDouble("varde");
								
				list.add(new DefaulterData(defaulter, typ, varde));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close();
		
		return Collections.unmodifiableList(list);
		
	}
	
	public void insertDefaulterData(String defaulter, String typ, String varde) {
		open();
		try {

			String sql;
			sql = "INSERT INTO Defaulter (xdefault,typ,varde) VALUES (" + "'" + defaulter+
					"'" + "," + "'" + typ + "'" + "," + varde + ");";
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	public void updateDefaulterData(String defaulter, String typ, String varde) {
		open();
		try {

			String sql;
			sql = "UPDATE Defaulter SET xdefault=" + "'" + defaulter + "'" + ",varde=" + varde + " where typ=" + "'" + typ + "'" + ";";
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	public void deleteDefaulterData(String typ) {
		open();
		try {

			String sql;
			sql = "DELETE FROM Defaulter where typ=" + "'" + typ + "'"  +  ";";
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}

	public List<KalenderData> getKalenderData(){
		open();
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
		
		close();
		return Collections.unmodifiableList(list);
		
	}
	
	public void updateKalender(Date date, int dagtyp) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = "";
		strDate = formatter.format(date);
		//System.out.println(strDate);
		open();
		try {

			String sql;
			sql = "UPDATE Kalender SET dagtyp=" + dagtyp +" WHERE data_date=" + strDate +";";
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close();
	}


	public void insertDagtData(String typ, String timme, String tmin, String tmax){
		open();
		try {
			
			String sql;
			sql = "INSERT INTO Dagtyp (dagtyp,timme,Tmin,Tmax) VALUES (" + typ+ "," + timme + "," + tmin + "," + tmax + ");";
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	} 
	
	public void deleteDagtData(String dagtyp, String timme) {
		open();
		try {

			String sql;
			sql = createDeleteStatement(dagtyp, timme);
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
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
		open();
		try {

			String sql;
			sql = createUpdateStatement(dagtyp, timme, tmin, tmax);
			rs = stmt.executeQuery(sql);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
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
	
	public boolean ifKeyDefaulterExists(String typ) {
		boolean exists = false;
		open();
		try {

			String sql = "SELECT typ FROM Defaulter WHERE typ=" + "'" + typ + "'" + ";";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {

				String typstr = rs.getString("typ");
				
				if (typ.equals(typstr)) {
					exists = true;
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		close();

		return exists;
		
		
	}
	
	public boolean ifKeyDagtExists(String dagtyp, String timme) {
		boolean exists = false;
		open();
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
		close();
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
		open();
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
		close();
		return Collections.unmodifiableList(list);
		
	}
	
	


	public List<ResData> getResultatData(String fromDate, String hour) {
		List<ResData> list = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String bertidStr = "";
		open();
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
		close();
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
		open();
		try {
			
			String sql;
			sql = createBetweenDatesStatement(dateFrom, dateTo);
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Date date = rs.getDate("data_date");
				int timme = rs.getInt("SMHItimme");
				double temp = rs.getDouble("SMHItemp");
				double kost = rs.getDouble("NORDPpris");
				int tmin = rs.getInt("Tmin");
				int tmax = rs.getInt("Tmax");

				
				list.add(new Data(date, timme, kost, temp, tmin, tmax));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return Collections.unmodifiableList(list); 
		
	}
	
	
	private String createBetweenDatesStatement(String dateFrom, String dateTo) {
		StringBuilder str = new StringBuilder();
		
		str.append("select a.data_date, b.SMHItimme, b.SMHItemp, c.NORDPpris, d.Tmax, d.Tmin from Kalender a\n"
				+ "join (SMHI b, NORDP c, Dagtyp d) on (a.data_date=b.data_date and a.data_date=c.data_date and b.SMHItimme=c.NORDPtimme \n"
				+ "and b.SMHItimme=d.timme and a.dagtyp=d.dagtyp)\n"
				+ "where a.data_date>=" +
				dateFrom + " and a.data_date<=" + dateTo + ";");
		return str.toString();
	
		}
	
	//GRAF HDMI
	public Map<String, Integer> getSMHIBetweenDates(String fromDate, String toDate){
			
		Map<String, Integer> SMHIdata = new LinkedHashMap<String, Integer>();
		open();
		try {
			
			String sql;
			sql = createSMHIBetweenDatesStatement(fromDate, toDate);
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Date date = rs.getDate("date");
				int timme = rs.getInt("hour");
				//double temp = rs.getDouble("SMHItemp");
				int temp = rs.getInt("temp");
				
				//int dateWithHour = Integer.valueOf(createDateWithHour(date, timme));
				SMHIdata.put(createDateWithHour(date, timme), temp);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return Collections.unmodifiableMap(SMHIdata); 
			
	}
	
		
	private String createSMHIBetweenDatesStatement(String fromDate, String toDate) {
		StringBuilder str = new StringBuilder();
		
		str.append("select data_date as Date, SMHItimme as Hour , SMHItemp  as Temp from SMHI where"
				+ " data_date between "+ fromDate + " and " + toDate +";");
		return str.toString();
		
	}
	
	//GRAF OPTIMERING
	public Map<String, Integer> getOptBetweenDates(String fromDate, String toDate){
		
		Map<String, Integer> Optdata = new LinkedHashMap<String, Integer>();
		open();
		try {
			
			String sql;
			sql = createOptBetweenDatesStatement(fromDate, toDate);
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Date date = rs.getDate("date");
				int timme = rs.getInt("hour");
				int temp = rs.getInt("temp");
				
				Optdata.put(createDateWithHour(date, timme), temp);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close();
		return Collections.unmodifiableMap(Optdata); 
				
	}
	
	private String createOptBetweenDatesStatement(String fromDate, String toDate) {
		StringBuilder str = new StringBuilder();
		str.append("select data_date as Date, Rtimme as Hour, Rtemp as Temp from "
				+ "resultat where Rmin=0 and not typ='TOSP' and data_date between "+ fromDate + " and " + toDate +";");
		return str.toString();
		
	}
	
	//UTOMHUSTEMP GRAF
	public Map<String, Integer> getUtempBetweenDates(String fromDate, String toDate){
		
		Map<String, Integer> utempData = new LinkedHashMap<String, Integer>();
		open();
		try {
			
			String sql;
			sql = createUtempBetweenDatesStatement(fromDate, toDate);
			//System.out.println(sql + "!");
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Date date = rs.getDate("date");
				int timme = rs.getInt("hour");
				int temp = rs.getInt("temp");
				
				utempData.put(createDateWithHour(date, timme), temp);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return Collections.unmodifiableMap(utempData); 
				
	}
	
	private String createUtempBetweenDatesStatement(String fromDate, String toDate) {
		StringBuilder str = new StringBuilder();
		str.append("select DATE_FORMAT(mattid, \"%Y-%m-%d\") as Date, DATE_FORMAT(mattid, \"%H\") as Hour, avg(ute) as Temp "
				+ "from Matdata where DATE_FORMAT(mattid, \"%Y%m%d\")between "+ fromDate + " and " + toDate 
				+" group by DATE_FORMAT(mattid, \"%Y%m%d\"), Hour;");
		return str.toString();
	}
	
	//INNETEMP GRAF
	public Map<String, Integer> getItempBetweenDates(String fromDate, String toDate){
		
		Map<String, Integer> itempData = new LinkedHashMap<String, Integer>();
		open();
		try {
			
			String sql;
			sql = createItempBetweenDatesStatement(fromDate, toDate);
			//System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				Date date = rs.getDate("date");
				int timme = rs.getInt("hour");
				int temp = rs.getInt("temp");
				
				itempData.put(createDateWithHour(date, timme), temp);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		
		return Collections.unmodifiableMap(itempData);  //itempData
				
	}
	
	private String createItempBetweenDatesStatement(String fromDate, String toDate) {
		StringBuilder str = new StringBuilder();
		str.append("select DATE_FORMAT(mattid, \"%Y-%m-%d\") as Date, DATE_FORMAT(mattid, \"%H\") as Hour, avg(inne) as Temp  \n"
				+ "from Matdata where DATE_FORMAT(mattid, \"%Y%m%d\") between "+ fromDate + " and " + toDate 
				+" group by DATE_FORMAT(mattid, \"%Y%m%d\"), Hour;");
		return str.toString();
	}
	
	
	private String createDateWithHour(Date date, int hour) {
		StringBuilder str = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
        String strDate = formatter.format(date);
        String strHour = Integer.toString(hour);
        str.append(strDate + strHour);
		return str.toString();
	}
	
	public List<SimData> getSimData(String date, String timme, String id, String temp) {
	
		List<SimData> list = new ArrayList<>(); 
		
		try {
			
			String sql;
			sql = createSimDataStatement(date, timme, id);
			//System.out.println(sql);
			
			double paaAlfa = getpaaAlfa();
			double avBeta = getavBeta();
			
			open();
			rs = stmt.executeQuery(sql); //here
			
			
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
		close();
		return Collections.unmodifiableList(list); 
		
	}
	
	public double getavBeta() {
		double avBeta = 0;
		open();
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
		close();
		return avBeta;
	}
	
	public double getpaaAlfa() {
		double paaAlfa = 0;
		open();
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
		close();
		return paaAlfa;
	}
	
	public void updateAlfaBeta(String alfa, String beta) {
		open();
		try {
			
			String sql;
			sql = "UPDATE Parametrar SET paaAlfa=" + alfa + ",avBeta=" + beta + ";";
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
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
	

	

}
