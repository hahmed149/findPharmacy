package com.Hamza.findPharmacy.Utils;


import com.Hamza.findPharmacy.models.LocationRequest;
import com.Hamza.findPharmacy.models.LocationResponse;
import com.Hamza.findPharmacy.models.Pharmacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;

import static com.Hamza.findPharmacy.Utils.PharmacyLookUtils.closestPharmacy;
import static com.Hamza.findPharmacy.Utils.PharmacyLookUtils.haversine;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PharmacyLookUtilsTests {

     private Pharmacy pharm;
     private List<Pharmacy> pharmacyDirectory = new ArrayList<>();
     private LocationRequest requestedLocation;

    @BeforeEach
    public void setup() throws Exception {
         pharm = new Pharmacy("3 PHARMACY LLC",
                "8221 SW 6TH AVE",
                "TOPEKA",
                "KS",
                66603,
                34.05433,
                -95.68453);

        pharmacyDirectory.add(pharm);
        requestedLocation  = new LocationRequest(34.05433,-96.68453);
    }
    @Test
    public void closestPharmacy_withValidData () {
        LocationResponse response = closestPharmacy(pharmacyDirectory,requestedLocation);
        LocationResponse expected = new LocationResponse("3 PHARMACY LLC","8221 SW 6TH AV",14.556);
        assertEquals(expected.getName(), response.getName());
    }

    @Test
    public void closestPharmacy_withNullRequestedLocationData () {
        assertNull(closestPharmacy(pharmacyDirectory,null));
    }
    @Test
    public void closestPharmacy_withNullPharmacyDirectory () {
        assertNull(closestPharmacy(null, requestedLocation));
    }
    @Test
    public void closestPharmacy_withInvalidData (){
        assertNull(closestPharmacy(null, null));
    }

    @Test
    public void haversine_returnsCorrectDistance(){
        double distanceResult = haversine(requestedLocation,pharm);
        assertEquals(57.244, distanceResult,0.005);
    }

    @Test
    public void haversine_returnsZeroForNullRequestedLocation(){
        double distanceResult = haversine(null,pharm);
        assertEquals(0.00, distanceResult,0.005);
    }

    @Test
    public void haversine_returnsZeroForNullPharmacy(){
        double distanceResult = haversine(requestedLocation,null);
        assertEquals(0.0, distanceResult,0.005);
    }
    @Test
    public void haversine_returnsZeroForInvalidData(){
        double distanceResult = haversine(null,null);
        assertEquals(0.0, distanceResult,0.005);
    }
}
