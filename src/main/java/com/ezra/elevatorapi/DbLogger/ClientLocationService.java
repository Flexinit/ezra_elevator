package com.ezra.elevatorapi.DbLogger;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;

@Service
public class ClientLocationService {

    private final DatabaseReader databaseReader;

    public ClientLocationService() throws Exception {
        // Load the MaxMind GeoIP2 database
        File database = new File("GeoLite2-City.mmdb");
         databaseReader = new DatabaseReader.Builder(database).build();

        //URL databaseUrl = getClass().getResource("/GeoLite2-City.mmdb");
        //System.out.println("Database URL: " + databaseUrl);
        //File databaseFile = new File(databaseUrl.toURI());
        //databaseReader = new DatabaseReader.Builder(databaseFile).build();
    }

    public String getClientLocation(HttpServletRequest request) {
        // Get the client's IP address from the request
        String ipAddress = request.getRemoteAddr();

        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            // Look up the location using the GeoIP2 database
            CityResponse cityResponse = databaseReader.city(inetAddress);
            City city = cityResponse.getCity();

            // Extract relevant location information (e.g., city, country)
            String cityName = city.getName();
            String countryName = cityResponse.getCountry().getName();

            return cityName + ", " + countryName;
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
