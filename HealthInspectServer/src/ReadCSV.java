import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class ReadCSV 
{
	private static String csvFile;
	private BufferedReader br;
	private String csvSplitBy;
	private static HashMap<String, Restaurant> restaurantList = new HashMap<>();
	
	
	public ReadCSV()
	{
		csvFile = "../CSV File/Food_Inspections.csv";
		csvSplitBy = ",";
		br = null;
	}
	
	public void readCSV() 
	{
		String line = "";
		try 
		{
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			
			while ((line = br.readLine()) != null) 
			{
				String[] restaurants = line.split(csvSplitBy);
				if (restaurants.length == 17
						&& !(Arrays.asList(restaurants).contains(""))
						&& !(restaurants[12].equals("Out of Business"))) 
				{
					String restKey = restaurants[14] + restaurants[15];
					if(restaurantList.containsKey(restKey))
					{
						if(parseDate(restaurants[10]).after(restaurantList.get(restKey).inspection_date))
						{
							restaurantList.replace(restKey, new Restaurant(
								Integer.parseInt(restaurants[0]), 
								restaurants[1],
								restaurants[2], 
								Integer.parseInt(restaurants[3]),
								restaurants[4], 
								restaurants[5], 
								restaurants[6],
								restaurants[7], 
								restaurants[8], 
								Integer.parseInt(restaurants[9]),
								parseDate(restaurants[10]), 
								restaurants[11],
								restaurants[12], 
								Double.parseDouble(restaurants[14]), 
								Double.parseDouble(restaurants[15]),
								new Point(Double.parseDouble(restaurants[14]),Double.parseDouble(restaurants[15])),
								restaurants[13]));
						}
						continue;
					}
					restaurantList.put(restKey, new Restaurant(
							Integer.parseInt(restaurants[0]), 
							restaurants[1],
							restaurants[2], 
							Integer.parseInt(restaurants[3]),
							restaurants[4], 
							restaurants[5], 
							restaurants[6],
							restaurants[7], 
							restaurants[8], 
							Integer.parseInt(restaurants[9]),
							parseDate(restaurants[10]), 
							restaurants[11],
							restaurants[12], 
							Double.parseDouble(restaurants[14]), 
							Double.parseDouble(restaurants[15]),
							new Point(Double.parseDouble(restaurants[14]),Double.parseDouble(restaurants[15])),
							restaurants[13]));
				} else {
					continue;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Date parseDate(String date) throws ParseException
	{
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		Date myDate = formatter.parse(date);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(myDate);
		
		return calendar.getTime();
		
	}
	
	public ArrayList<Restaurant> getViewableRestaurants(Point p1, Point p2)
	{
		ArrayList<Restaurant> viewable = new ArrayList<>();
		Iterator<String> it = restaurantList.keySet().iterator();
		double viewTop = Math.max(p1.lat,p2.lat);
		double viewBot = Math.min(p1.lat,p2.lat);
		double viewLeft = Math.max(p1.lang, p2.lang);
		double viewRight = Math.min(p1.lang,p2.lang);
		String current = null;
		for(current = it.next();it.hasNext();current = it.next())
		{
			if(restaurantList.get(current).latitude <= viewTop &&
					restaurantList.get(current).latitude >= viewBot)
			{
				if(restaurantList.get(current).longitude <= viewLeft &&
					restaurantList.get(current).longitude >= viewRight)
				{
					viewable.add(restaurantList.get(current));
				}
			}
		}
		
		return viewable;
	}
	
	public HashMap<String,Restaurant> getRestaurantList()
	{
		return restaurantList;
	}

	public static HashMap<String,Restaurant> getInstance()
	{
		return new HashMap<String,Restaurant>(restaurantList);
	}
}
