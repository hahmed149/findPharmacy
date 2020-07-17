package com.Hamza.findPharmacy.controllers;

import com.Hamza.findPharmacy.models.Pharmacy;
import com.Hamza.findPharmacy.repositories.PharmacyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @GetMapping
    public List<Pharmacy> list() {
        return pharmacyRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Pharmacy get(@PathVariable Long id)
    {
        return  pharmacyRepository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  Pharmacy create(@RequestBody final Pharmacy pharmacy)
    {
        return  pharmacyRepository.saveAndFlush(pharmacy);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id)
    {
        pharmacyRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Pharmacy update(@PathVariable Long id, @RequestBody Pharmacy pharmacy) {
        Pharmacy existingPharmacy = pharmacyRepository.getOne(id);
        BeanUtils.copyProperties(pharmacy,existingPharmacy,"id");
        return  pharmacyRepository.saveAndFlush(existingPharmacy);
    }
}
