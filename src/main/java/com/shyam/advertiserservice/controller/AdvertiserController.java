package com.shyam.advertiserservice.controller;


import com.shyam.advertiserservice.exceptions.ErrorResponse;
import com.shyam.advertiserservice.model.Advertiser;
import com.shyam.advertiserservice.repository.AdvertiserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class AdvertiserController {


    @Autowired
    @Setter
    private AdvertiserRepository repository;

    @GetMapping(value = "/api/advertiser",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Advertiser>> getAllAdvertiser() throws Throwable {
        return new ResponseEntity<List<Advertiser>>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/api/advertiser/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Advertiser> getAdvertiser(@PathVariable long id) throws Throwable {
        return new ResponseEntity<Advertiser>(repository.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping(value = "/api/advertiser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Advertiser> saveAdvertiser(@RequestBody Advertiser advertiser) throws Throwable {
        Advertiser savedAdvertiser = repository.save(advertiser);
        return new ResponseEntity<Advertiser>(savedAdvertiser, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/advertiser/{id}",
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

    @DeleteMapping("/api/advertiser/{id}")
    public ResponseEntity<String> deleteAdvertiser(@PathVariable("id") long advertiserId) throws Throwable {
        repository.deleteById(advertiserId);
        return new ResponseEntity<>("Advertiser has been removed successfully !!", HttpStatus.OK);
    }

    @GetMapping("/api/advertiser/{id}/validate")
    public ResponseEntity<String> checkCreditLimit(@PathVariable("id") long advertiserId) throws Throwable {
            Optional<Advertiser> advertiser = repository.findById(advertiserId);
            if (!advertiser.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            if (advertiser.get().getAdvertiserCreditLimit() > 0) {
                return new ResponseEntity<>("true", HttpStatus.OK);
            }

            return new ResponseEntity<>("false", HttpStatus.OK);


    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidRequestParameterOrBody(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(String.format("Invalid value %s in field %s",
                        e.getBindingResult().getFieldError().getRejectedValue(),
                        e.getBindingResult().getFieldError().getField())).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNoSuchElement(NoSuchElementException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(HttpStatus.NO_CONTENT.value())
                .message("Resource Not found").build(),
                HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageConversionException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(HttpStatus.NO_CONTENT.value())
                .message("Invalid request").build(),
                HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ErrorResponse.INTERNAL_ERROR_MESSAGE).build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
