package com.develhope.spring.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<RentInfo, Long> {

    void deleteByCustomer_Id(Long userId);
}