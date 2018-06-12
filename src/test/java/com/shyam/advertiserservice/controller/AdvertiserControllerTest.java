package com.shyam.advertiserservice.controller;

import com.jayway.restassured.response.Header;
import com.shyam.advertiserservice.model.Advertiser;
import com.shyam.advertiserservice.repository.AdvertiserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AdvertiserControllerTest {

    private AdvertiserController controller;
    private Advertiser advertiser;
    private String msg;
    @Mock
    private AdvertiserRepository repository;

    private List<Advertiser> advertiserList;
    @Before
    public void setUp() throws Exception {

        controller = new AdvertiserController();
        controller.setRepository(repository);
        advertiser = Advertiser.builder()
                //.id(1L)
                .advertiserName("GM")
                .advertiserContactName("Jimmy")
                .advertiserCreditLimit(100L)
                .build();
        advertiserList = new ArrayList<>();
        advertiserList.add(advertiser);
    }

    @Test
    public void AddAdvertiser_infornation_createNewAdvertiser(){
        msg="Advertiser has been Saved Successfully";
        Mockito.when(repository.save(Mockito.any())).thenReturn(advertiser);


        given()
                .standaloneSetup(controller)
                .header(new Header("Content-Type", "application/json"))
                .body(advertiser)
                .when()
                .post("/api/advertiser")
                .then()
                .statusCode(HttpStatus.CREATED.value());

    }

    @Test
    public void listAllAdvertisers_infornation_returnAllAdvertisers(){
        Mockito.when(repository.findAll()).thenReturn(advertiserList);


        given()
                .standaloneSetup(controller)
                .header(new Header("Content-Type", "application/json"))
                .when()
                .get("/api/advertiser")
                .then()
                .statusCode(HttpStatus.OK.value());


    }

    @Test
    public void findAdvertisers_returnAdvertiser_information(){
        Mockito.when(repository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(advertiser));


        given()
                .standaloneSetup(controller)
                .header(new Header("Content-Type", "application/json"))
                .when()
                .get("/api/advertiser/1")
                .then()
                .statusCode(HttpStatus.OK.value());


    }

    @Test
    public void updateAdvertiser_information_returnAdvertiser_Updated_information(){
        Mockito.when(repository.findById(Mockito.any())).thenReturn(java.util.Optional.ofNullable(advertiser));
        Advertiser updatedAdvertiser;
        updatedAdvertiser=advertiser;
        updatedAdvertiser.setAdvertiserName("GM-Updated");

        Mockito.when(repository.save(Mockito.any())).thenReturn(advertiser);

        given()
                .standaloneSetup(controller)
                .header(new Header("Content-Type", "application/json"))
                .body(advertiser)
                .when()
                .put("/api/advertiser/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("advertiserName", org.hamcrest.Matchers.is("GM-Updated"));
    }

    @Test
    public void deleteAdvertisers_returnSuccessMessage(){
        given()
                .standaloneSetup(controller)
                .header(new Header("Content-Type", "application/json"))
                .when()
                .delete("/api/advertiser/1")
                .then()
                .statusCode(HttpStatus.OK.value());


    }

    @Test
    public void postAdvertiser_returns400_whenInvalidRequestBodySent() {
        given()
                .standaloneSetup(controller)
                .header(new Header("Content-Type", "application/json"))
                .body("")
                .when()
                .post("/api/advertiser")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}