package com.Hamza.findPharmacy.repositories;

import com.Hamza.findPharmacy.models.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {
}
