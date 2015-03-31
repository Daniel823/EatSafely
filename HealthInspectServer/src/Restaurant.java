import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Restaurant 
{
	public Integer id, license,zip;
	public String name, aka_name, facility,risk,adress,city,state,inspection_type,results,comments;
	public Double latitude, longitude;
	public Point location;
	public Date inspection_date;

	public Restaurant(Integer id, String name, String aka_name,
			Integer license, String facility, String risk, String address,
			String city, String state, Integer zip, Date inspection_date,
			String inspection_type, String results, Double latitude,
			Double longitude, Point location, String violations) 
	{
		this.id = id;
		this.name = name;
		this.aka_name = aka_name;
		this.license = license;
		this.facility = facility;
		this.risk = risk;
		this.adress = address;
		this.city =city;
		this.state = state;
		this.zip = zip;
		this.inspection_date = inspection_date;
		this.inspection_type = inspection_type;
		this.results = results;
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = location;
		this.comments = violations;
	}
	
	public String output()
	{
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String myDate = formatter.format(inspection_date);
		String result = Double.toString(latitude) + "," + Double.toString(longitude) + ",";
		if(results.equals("Pass") || results.equals("Pass w/ Conditions"))
			result += "1";
		else
			result += "0";
		result += "," + aka_name;
		result += "," + myDate;
		return result + ";";
	}
	
	public String detailedInfo()
	{
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String myDate = formatter.format(inspection_date);
		
		String info = aka_name + "," + adress + "," + results + "," + myDate + "," + comments;
		
		return info;
	}

}