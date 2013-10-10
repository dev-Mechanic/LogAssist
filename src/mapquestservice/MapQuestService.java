/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapquestservice;



import entity.Destination;
import entity.Route;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

 
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


/**
 *
 * @author harryp
 */
public class MapQuestService {

    /**
     * @param args the command line arguments
     */
    
    private static String RequestURL = "http://open.mapquestapi.com/directions/v1/route?ambiguities=ignore&unit=k&narrativeType=none";
    private static String APIKey = "Fmjtd%7Cluub2h62l9%2C8a%3Do5-9uzxgz";
    private static String EncodeFMT = "ISO-8859-1";
    
    private static ArrayList <Route>LocalRouteDistanceRepo = new ArrayList();
    private static ArrayList <String>RouteCache = new ArrayList();
    
    //public static void main(String[] args) {
        // TODO code application logic here
        
//        GetRoute("5 Warley Road Malvern East VIC 3145","800 Stud Road Scoresby VIC 3179").printRoute();
//        GetRoute("5 Warley Road Malvern East VIC 3145","800 Stud Road Scoresby VIC 3179").printRoute();
//        GetRoute("5 Warley Road Malvern East VIC 3145","35 Koornang Road Scoresby VIC 3179").printRoute();
//        GetRoute("5 Warley Road Malvern East VIC 3145","800 Stud Road Scoresby VIC 3179").printRoute();
//        GetRoute("5 Warley Road Malvern East VIC 3145","800 Stud Road Scoresby VIC 3179").printRoute();
   // }
    
    
    
    
    
    private static Route GetRoute(String from, String to)
    {
        try
        {
            Route currentRoute;
             
            if(RouteCache.contains(from.hashCode()+"-"+to.hashCode()))
            {
                currentRoute = LocalRouteDistanceRepo.get(RouteCache.indexOf(from.hashCode()+"-"+to.hashCode()));
                return currentRoute;
            }
            else
            {
                String jsonResponse = APICall(from,to);
                JSONObject jsonObj = new JSONObject(jsonResponse);
                JSONArray routeLocations = new JSONArray((jsonObj.getJSONObject("route")).getString("locations"));
                double distance = (jsonObj.getJSONObject("route")).getDouble("distance");

                // From Location Coordinates
                JSONObject LatLngFrom = routeLocations.getJSONObject(0).getJSONObject("latLng");
                Destination fromDest = new Destination(from,LatLngFrom.getDouble("lat"),LatLngFrom.getDouble("lng"));


                // To Location Coordinates
                JSONObject LatLngTo = routeLocations.getJSONObject(1).getJSONObject("latLng");
                Destination toDest = new Destination(to,LatLngFrom.getDouble("lat"),LatLngFrom.getDouble("lng"));


                currentRoute = new Route(fromDest,toDest,distance);

                RouteCache.add(from.hashCode()+"-"+to.hashCode());
                LocalRouteDistanceRepo.add(currentRoute);
                
                return currentRoute;
            }
        
        }
        catch(Exception ex)
        {
            System.out.println(" Get Route Exception MapQuest : " + ex.getMessage());
            return null;
        }
        
    }
    
    private static String APICall(String from,String to) {
                
        try
        {
		String url = RequestURL + "&key=" + APIKey
                                        + "&from=" + URLEncoder.encode(from,EncodeFMT)
                                        + "&to=" + URLEncoder.encode(to,EncodeFMT);
 
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
 
		// add request header
		//request.addHeader("User-Agent", USER_AGENT);
 
		HttpResponse response = client.execute(request);
 
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + 
                //       response.getStatusLine().getStatusCode());
 
		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
 
		return result.toString();
        }
        catch(Exception ex)
        {
            return ex.getMessage();
        }
        
	}
}
