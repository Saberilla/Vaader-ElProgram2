import java.util.Date;

public class ResData {
	
	private String bertid;
	private String typ;
	private int alt_id;
	private Date date;
	private int timme;
	private double temp;
	
	public ResData(String bertid, String typ, int alt_id, Date date, int timme, double temp ) {
		this.bertid = bertid;
		this.typ = typ; 
		this.alt_id = alt_id;
		this.date = date;
		this.timme = timme;
		this.temp = temp;
		}
	
	public String getBertid() {
		return this.bertid;
	}
	
	public String getTyp() {
		return this.typ;
	}
	
	public int getAlt_id() {
		return this.alt_id;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getTimme() {
		return this.timme;
	}
	
	public double getTemp() {
		return this.temp;
	}
	
	

}
