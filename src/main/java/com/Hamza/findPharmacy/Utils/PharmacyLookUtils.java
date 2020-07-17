package com.Hamza.findPharmacy.Utils;

import com.Hamza.findPharmacy.models.LocationRequest;
import com.Hamza.findPharmacy.models.LocationResponse;
import com.Hamza.findPharmacy.models.Pharmacy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class PharmacyLookUtils {

    public static LocationResponse closestPharmacy (List<Pharmacy> allPharmacies, LocationRequest requestedLocation)
    {
        if(requestedLocation != null && allPharmacies != null )
        {
            LinkedHashMap<Pharmacy, Double> distanceMap = new LinkedHashMap<>();

            for (Pharmacy pharmacy : allPharmacies) {
                distanceMap.put(pharmacy, haversine(requestedLocation, pharmacy));
            }

            // sorting the distances in ascending order
            Map<Pharmacy, Double> distanceMapSorted = distanceMap.entrySet().stream().sorted(comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p2, LinkedHashMap::new));

            Map.Entry<Pharmacy, Double> closestPharmacy = distanceMapSorted.entrySet().iterator().next();
            String pharmacyName = closestPharmacy.getKey().getName();
            String pharmacyAddress = closestPharmacy.getKey().getAddress();
            Double pharmacyDistance = closestPharmacy.getValue();
            LocationResponse response = new LocationResponse(pharmacyName, pharmacyAddress, pharmacyDistance);
            return response;
        }
        return null;
    }

    public static double haversine(LocationRequest requestedLocation, Pharmacy pharmacyToCheck)
    {
        if(requestedLocation != null && pharmacyToCheck!=null) {

            // read in request location coordinates
            double latitude1 = requestedLocation.getLatitude();
            double longitude1 = requestedLocation.getLongitude();

            // read in pharmacy coordinates
            double latitude2 = pharmacyToCheck.getLatitude();
            double longitude2 = pharmacyToCheck.getLongitude();

            // distance between latitudes and longitudes
            double distanceBetweenLatitudes = Math.toRadians(latitude2 - latitude1);
            double distanceBetweenLongitudes = Math.toRadians(longitude2 - longitude1);

            // convert coordinates to radians
            latitude1 = Math.toRadians(latitude1);
            latitude2 = Math.toRadians(latitude2);

            // apply formulae
            double a = Math.pow(Math.sin(distanceBetweenLatitudes / 2), 2) +
                    Math.pow(Math.sin(distanceBetweenLongitudes / 2), 2) *
                            Math.cos(latitude1) *
                            Math.cos(latitude2);
            double rad = 3958.756; // this will get us distance in miles
            double c = 2 * Math.asin(Math.sqrt(a));
            return rad * c;
        }
        return 0.0;
    }

}
