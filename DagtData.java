
public class DagtData {
	
	private int typ;
	private int timme;
	private int tmin;
	private int tmax;
	
	public DagtData(int typ, int timme, int tmin, int tmax) {
		this.typ = typ;
		this.timme = timme;
		this.tmin = tmin;
		this.tmax = tmax;
	}

	public int getDagtyp() {
		return typ;
	}

	public int getTimme() {
		return timme;
	}

	public int getTmin() {
		return tmin;
	}

	public int getTmax() {
		return tmax;
	}
	
	

}
