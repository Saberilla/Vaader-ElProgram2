import java.util.Date;

public class KalenderData {
	
	private Date date;
	private int dagTyp;
	
	public KalenderData(Date date, int dagTyp){
		this.date = date;
		this.dagTyp = dagTyp;
	}

	public Date getDate() {
		return date;
	}

	public int getDagTyp() {
		return dagTyp;
	}
	
	

}
