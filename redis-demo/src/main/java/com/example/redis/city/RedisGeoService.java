package com.example.redis.city;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisGeoService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisGeoService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // add Locations, delete Locations, query Locations within a radius, etc.
    public List<City> getLocations(String key, List<String> cityList) {

        String[] cities = cityList.toArray(new String[0]);
        List<Point> position = redisTemplate.opsForGeo().position(key, cities);
        if (position == null) {
            return null;
        }

        List<City> result = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            Point point = position.get(i);
            if (point != null) {
                City city = new City();
                city.setLongitude(point.getX());
                city.setLatitude(point.getY());
                city.setName(cities[i]);
                result.add(city);
            } else {
                City city = new City();
                city.setName(cities[i]);
                result.add(city);
            }
        }

        return result;
    }

    public void addLocations(String key, List<City> cityList) {

       Map<Object, Point> cityPointMap = new HashMap<>();
         for (City city : cityList) {
             Point point = new Point(city.getLongitude(), city.getLatitude());
             cityPointMap.put(city.getName(), point);
         }

        redisTemplate.opsForGeo().add(key, cityPointMap);
    }

    public void deleteLocations(String key, List<String> cityList) {
        redisTemplate.opsForGeo().remove(key, cityList.toArray());
    }

    public void searchLocations(String key, String city, double radiusInKm) {

        Distance distance = new Distance(radiusInKm, Metrics.KILOMETERS);
        GeoReference<Object> member = GeoReference.fromMember(city);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> search = redisTemplate.opsForGeo().search(key, member, distance);
    }

}
