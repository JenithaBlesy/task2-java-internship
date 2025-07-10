import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            String apiKey = "975676121a86c03a54dc35a481127b25";  // Your API key
            String city = "London";
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder responseContent = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                responseContent.append(inputLine);
            }

            in.close();

            JSONObject json = new JSONObject(responseContent.toString());
            JSONObject main = json.getJSONObject("main");

            double temperature = main.getDouble("temp");
            int humidity = main.getInt("humidity");
            int pressure = main.getInt("pressure");

            System.out.println("--------- Weather Report ---------");
            System.out.println("City       : " + city);
            System.out.println("Temperature: " + temperature + " °C");
            System.out.println("Humidity   : " + humidity + "%");
            System.out.println("Pressure   : " + pressure + " hPa");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
