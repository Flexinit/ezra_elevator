package com.ezra.elevatorapi.DbLogger;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
public class ClientLocationService {

    private final DatabaseReader databaseReader;

    public ClientLocationService() throws IOException {
        // Load the MaxMind GeoIP2 database
        databaseReader = new DatabaseReader.Builder(getClass().getResourceAsStream("/GeoLite2-City.mmdb"))
                .build();
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
            // Handle any exceptions that occur during the location lookup
            e.printStackTrace();
            return "Unknown";
        }
    }
}
