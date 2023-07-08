package com.ezra.elevatorapi.DbLogger;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class IPGeolocationService {

    private final DatabaseReader databaseReader;

    public IPGeolocationService() throws IOException {
        File database = new File("GeoLite2-City.mmdb");
        databaseReader = new DatabaseReader.Builder(database).build();
    }

    public String getUserLocation(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CityResponse response = databaseReader.city(inetAddress);
            return response.getCity().getName() + ", " + response.getCountry().getName();
        } catch (IOException | GeoIp2Exception e) {
            // Handle exception
            return "Unknown";
        }
    }
}
