import java.util.ArrayList;
import java.util.Iterator;


public class Driver {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//ArrayList<Restaurant> resList = new ArrayList<Restaurant>();
		
		ReadCSV restaurantList = new ReadCSV();
		//resList = restaurantList.readCSV();
		restaurantList.readCSV();
		//Iterator<String> it = restaurantList.getRestaurantList().keySet().iterator();
		//Restaurant myRes = restaurantList.getRestaurantList().get(it.next());
		//System.out.println(myRes.adress);
		//System.out.println(myRes.aka_name + "\n" + myRes.inspection_date);
		//System.out.println(x.getRestaurantList().size());
		//Double a = 46.789;
		//Double b = 45.576;
		//Point p1 = new Point(41.929053,-87.653844);
		//Point p2 = new Point(41.918293,-87.643631);
		Point p1 = new Point(41.925472, -87.648747);
		Point p2 = new Point(41.929040,-87.653757);
		
		ArrayList<Restaurant> viewable = restaurantList.getViewableRestaurants(p1, p2);
		
		System.out.println(restaurantList.getRestaurantList().size());
		/*for(Restaurant r : viewable)
		{
			System.out.println(r.aka_name);
		}*/
		
	}
	
	public String findBounds(ArrayList<Restaurant> resList){
		System.out.println(resList.size());
		for (Restaurant i : resList){
			for(Restaurant j : resList){
				if(i.latitude == j.latitude && i.longitude == j.longitude){
					resList.remove(j);
				}
			}
		}
		System.out.println(resList.size());
		
		return " ";
	}

}
