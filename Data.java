import java.util.Date;

public class Data {
	
	private Date date;
	private int timme;
	private double kostnad;
	private int tmin;
	private int tmax;
	private double tempUpp;
	private double tempNed;
	
	public Data(Date date, int timme, double kostnad, int tmin, int tmax, double tempUpp, double tempNed) {
		this.date = date;
		this.timme = timme;
		this.kostnad = kostnad;
		this.tmin = tmin;	
		this.tmax = tmax;
		this.tempUpp = tempUpp;
		this.tempNed = tempNed;
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
	
	public double getTempUpp() {
		return this.tempUpp;
	}
	
	public double getTempNed() {
		return this.tempNed;
	}

}
