package com.shyam.advertiserservice.repository;

import com.shyam.advertiserservice.model.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser,Long>{

}
