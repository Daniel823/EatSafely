
public class Point {

	/**
	 * @param args
	 */
	public double lat,lang;
	
	public Point( double lat, double lang)
	{
		this.lat = lat;
		this.lang = lang;
	}

	public String getLoc()
	{
		return new String(Double.toString(lat) + Double.toString(lang));
	}
	
}
