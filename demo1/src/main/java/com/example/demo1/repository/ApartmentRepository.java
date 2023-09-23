package com.example.demo1.repository;

import com.example.demo1.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, String> {
}
