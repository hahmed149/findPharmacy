package com.Hamza.findPharmacy.controllers;

import com.Hamza.findPharmacy.models.LocationRequest;
import com.Hamza.findPharmacy.models.LocationResponse;
import com.Hamza.findPharmacy.models.Pharmacy;
import com.Hamza.findPharmacy.repositories.PharmacyRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.CsvToBean;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.Hamza.findPharmacy.Utils.PharmacyLookUtils.closestPharmacy;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * Pharmacy Controller
 * Contains:
 *  CRUD Operations for pharmacy model.
 *  Lookup utility to find the closest pharmacy.
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyRepository pharmacyRepository;

    /**
     * @Pharmacy List
     * @return list of all pharmacies in the database
     */
    @GetMapping
    public List<Pharmacy> list() {
        return pharmacyRepository.findAll();
    }

    /**
     * @Pharmacy at a given index
     * @param id - the Id of the pharmacy to retrieve
     * @return - pharmacy with matching id
     */
    @GetMapping
    @RequestMapping("{id}")
    public Pharmacy get(@PathVariable Long id)
    {
        return  pharmacyRepository.getOne(id);
    }

    /**
     * Creates a @Pharmacy and adds it to the database.
     * @param @Pharmacy  to add
     * @return the added @Pharmacy
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  Pharmacy create(@RequestBody final Pharmacy pharmacy)
    {
        return  pharmacyRepository.saveAndFlush(pharmacy);
    }

    /**
     * Deletes a Pharmacy from the database
     * @param id - Id of the pharmacy to delete
     */
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id)
    {
        pharmacyRepository.deleteById(id);
    }

    /**
     * Updates a @Pharmacy at a given index
     * @param id - id of the pharmacy to update.
     * @param pharmacy - @Pharmacy with updated details
     * @return - Updated @Pharmacy
     */
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Pharmacy update(@PathVariable Long id, @RequestBody Pharmacy pharmacy) {
        Pharmacy existingPharmacy = pharmacyRepository.getOne(id);
        BeanUtils.copyProperties(pharmacy,existingPharmacy,"id");
        return  pharmacyRepository.saveAndFlush(existingPharmacy);
    }

    /**
     * EndPoint for finding closest pharmacy to a requestedLocation
     * @param requestCoordinates - @LocationRequest coordinated from the user
     * @return - @LocationResponse containing the  closest @Pharmacy details
     */
    @RequestMapping (path = "findPharmacy", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LocationResponse findClosestPharmacy(@RequestBody final LocationRequest requestCoordinates)
    {
        List<Pharmacy> pharmacies = pharmacyRepository.findAll();
        return closestPharmacy(pharmacies,requestCoordinates);
    }

    /**
     * Endpoint for uploading a csv file that contains pharmacy information for populating the database
     * @param file - csv file to upload
     * @param model - spring framework UI Model
     * @return - @Model with status and message
     */
    @PostMapping(path = "uploadPharmacyFile")
    public String uploadPharmacyFiles (@RequestParam("file")MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `Pharmacy` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<Pharmacy> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Pharmacy.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of pharmacies
                List<Pharmacy> pharmacies = csvToBean.parse();

                // save pharmacies in DB
                for(Pharmacy p : pharmacies)
                {
                    pharmacyRepository.saveAndFlush(p);
                }

                // save pharmacies list on model
                model.addAttribute("pharmacies", pharmacies);
                model.addAttribute("status", true);


            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }
        return "Database appended with csv file data successfully.";
    }
}
