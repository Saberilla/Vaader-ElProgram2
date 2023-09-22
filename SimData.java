import java.util.Date;

public class SimData {
	
	private Date date;
	private int ordning;
	private int min;
	private int paav;
	private double kostnadTotal;
	private double kostnad;
	private int tmin;
	private int tmax;
	private int timme;
	private double temperaturTotal;
	private double tempUpp;
	private double tempNed;
	

	public SimData(Date date, int timme, double kostnad, int tmin, int tmax, double tempUpp, double tempNed,
			int ordning, int min, int paav, double kostnadTotal, double temperaturTotal) {

		this.date = date;
		this.timme = timme;
		this.kostnad = kostnad;
		this.tmin = tmin;
		this.tmax = tmax;
		this.tempUpp = tempUpp;
		this.tempNed = tempNed;
		this.ordning = ordning;
		this.min = min;
		this.paav = paav;
		this.kostnadTotal = kostnadTotal;
		this.temperaturTotal = temperaturTotal;
	}
	
	public int getOrdning() {
		return ordning;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getPaav() {
		return paav;
	}
	
	public double getKostnadTotal() {
		return this.kostnadTotal;
	}
	
	public double getTemperaturTotal() {
		return this.temperaturTotal;
	}

	public Date getDate() {
		return date;
	}

	public double getKostnad() {
		return kostnad;
	}

	public int getTmin() {
		return tmin;
	}

	public int getTmax() {
		return tmax;
	}

	public int getTimme() {
		return timme;
	}

	public double getTempUpp() {
		return tempUpp;
	}

	public double getTempNed() {
		return tempNed;
	}

}
