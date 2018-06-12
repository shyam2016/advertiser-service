package com.shyam.advertiserservice.controller;


import com.shyam.advertiserservice.model.Advertiser;
import com.shyam.advertiserservice.repository.AdvertiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdvertiserController {


    @Autowired
    private AdvertiserRepository repository;

    @GetMapping(value = "/advertiser",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Advertiser>> getAllAdvertiser() throws Throwable {
        return new ResponseEntity<List<Advertiser>>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/advertiser/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Advertiser> getAdvertiser(@PathVariable long id) throws Throwable {
        return new ResponseEntity<Advertiser>(repository.findById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/advertiser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> saveAdvertiser(@RequestBody Advertiser advertiser) throws Throwable {
        repository.save(advertiser);
        return new ResponseEntity<>("Advertiser has been Saved Successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/advertiser/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Advertiser> updateAdvertiser(@RequestBody Advertiser advertiser, @PathVariable("id") long advertiserId) throws Throwable {
        Optional<Advertiser> advertiserOptional = repository.findById(advertiserId);
        if (!advertiserOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        advertiser.setId(advertiserId);
        repository.save(advertiser);
        return new ResponseEntity<Advertiser>(advertiser, HttpStatus.OK);
    }

    @DeleteMapping("/advertiser/{id}")
    public ResponseEntity<String> deleteAdvertiser(@PathVariable("id") long advertiserId) throws Throwable {
        repository.deleteById(advertiserId);
        return new ResponseEntity<>("Advertiser has been removed successfully !!", HttpStatus.OK);
    }
}
