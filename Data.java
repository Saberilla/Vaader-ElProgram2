import java.util.Date;

public class Data {
	
	private Date date;
	private int timme;
	private double kostnad;
	private double temp;
	private int tmin;
	private int tmax;
	
	
	public Data(Date date, int timme, double kostnad, double temp, int tmin, int tmax) {
		this.date = date;
		this.timme = timme;
		this.kostnad = kostnad;
		this.tmin = tmin;	
		this.tmax = tmax;
		this.temp = temp;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	public int getTimme() {
		return this.timme;
	}
	
	public double getKostnad() {
		return this.kostnad;
	}
	
	public int getTmin() {
		return this.tmin;
	}
	
	public int getTmax() {
		return this.tmax;
	}
	

	public double getTemp() {
		return temp;
	}

}
