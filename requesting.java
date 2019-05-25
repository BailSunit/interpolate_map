import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;

public class requesting {
	public static void main(String[] args) throws IOException {
		URL requestUrl = new URL(
				"https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=Charlestown,MA|via:Lexington,MA&key=AIzaSyAb8ohmBXqtK4y2_a5CFnFnfLGiOsuwjIo");
		HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
		InputStream is = requestUrl.openConnection().getInputStream();
		connection.setRequestMethod("GET");
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		int index = 0;
		String line = null, response = null;
		String[] polyline = null;
		List<LatLng> coordinates = null;
		List<LatLng> interpolate = null;
		List<LatLng> interpolate2 = null;
		while ((line = reader.readLine()) != null) {
			if (line.contains("polyline")) {
				String res = reader.readLine().trim().substring(11);
				for (int i = 0; i < res.length(); i++) {
					if (res.charAt(i) == '\"')
						;
					else
						response += res.charAt(i);
				}
				// Decoding Polyline points for a single leg
				EncodedPolyline encodedPolyline = new EncodedPolyline(response);
				coordinates = encodedPolyline.decodePath();
				System.out.println(coordinates);
				for (int j = 0; j < coordinates.size(); j++) {
					interpolate.add(coordinates.get(j));
				}
			}
		}
		for (int i = 0; i < interpolate.size() - 1; i++) {
			interpolate2.add(Services.computeOffset(interpolate.get(i), 2.0,
					Services.computeHeading(interpolate.get(i), interpolate.get(i + 1))));
		}
		System.out.println(interpolate2);
	}

}
