package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Geolocation {
	
	/*
	 * CHANGE THE MAIN METHODS INTO A COUPLE DIFFERENT METHODS
	 * (getCountry)
	 */
	
	public String getCountry(String longitude, String latitude){
		return null;
	}

	public static void main(String[] args) {

		float lat, lon;

		Scanner in = new Scanner(System.in);

		System.out.println("Enter latitude: ");
		lat = in.nextFloat();
		System.out.println("Enter longitude: ");
		lon = in.nextFloat();

		// making url request
		try {
			URL url = new URL(
					"http://maps.googleapis.com/maps/api/geocode/json?latlng="
							+ lat + "," + lon + "&sensor=true");
			// making connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			// Reading data's from url
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			String out = "";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				// System.out.println(output);
				out += output;
			}
			// Converting Json formatted string into JSON object
			JSONObject json = (JSONObject) JSONSerializer.toJSON(out);
			JSONArray results = json.getJSONArray("results");
			JSONObject rec = results.getJSONObject(0);
			JSONArray address_components = rec
					.getJSONArray("address_components");
			for (int i = 0; i < address_components.size(); i++) {
				JSONObject rec1 = address_components.getJSONObject(i);
				// trace(rec1.getString("long_name"));
				JSONArray types = rec1.getJSONArray("types");
				String comp = types.getString(0);

				if (comp.equals("locality")) {
					// System.out.println("city ———— "+rec1.getString("long_name"));
				} else if (comp.equals("country")) {
					System.out.println("Your outputted country is:	"
							+ rec1.getString("long_name"));
				}
			}
			String formatted_address = rec.getString("formatted_address");
			// System.out.println("formatted_address———— "+formatted_address);

			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
