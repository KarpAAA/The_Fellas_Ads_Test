package com.example.the_fellas_ads_test.repository;

import com.example.the_fellas_ads_test.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
}
