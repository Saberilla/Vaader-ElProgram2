import java.util.Date;

public class SimData extends Data{
	
	private int ordning;
	private int min;
	private int paav;
	private double kostnadTotal;
	private double temperaturTotal;

	public SimData(Date date, int timme, double kostnad, int tmin, int tmax, double tempUpp, double tempNed,
			int ordning, int min, int paav, double kostnadTotal, double temperaturTotal) {
		super(date, timme, kostnad, tmin, tmax, tempUpp, tempNed);
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

}
