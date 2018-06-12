package com.shyam.advertiserservice.repository;

import com.shyam.advertiserservice.model.Advertiser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { ClientConfiguration.class },
        loader = AnnotationConfigContextLoader.class)
@EntityScan("com.shyam.advertiserservice.model")
@EnableJpaRepositories("com.shyam.advertiserservice.repository")
@DataJpaTest
public class AdvertiserRepositoryTest {

    @Autowired
    private  AdvertiserRepository repository;

    private  Advertiser advertiser;
    private  Advertiser advertiser2;
    private List<Advertiser> advertiserList;

    @Before
    public void setUp() throws Exception {

        advertiser = Advertiser.builder()
                //.id(1L)
                .advertiserName("GM")
                .advertiserContactName("Jimmy")
                .advertiserCreditLimit(100L)
                .build();

         advertiser2 = Advertiser.builder()
                //.id(2L)
                .advertiserName("Ford")
                .advertiserContactName("Tom")
                .advertiserCreditLimit(200L)
                .build();
        repository.save(advertiser);
        repository.save(advertiser2);
    }


    @Test
    public void searchAdvertiser_byId_whenGetMethodCalled_Return_Advertiser() {
        advertiserList = repository.findAll();
        Advertiser returnedAdvertiser = repository.findById(advertiserList.get(0).getId()).get();
        Assert.assertNotNull(returnedAdvertiser);
        Assert.assertEquals(returnedAdvertiser.getAdvertiserContactName() , "Jimmy");
    }

    @Test
    public void addAdvertiser_whenPostMethodCalled_Return_Advertiser(){
        Advertiser savedAdvertiser = repository.save(advertiser);
        Assert.assertEquals(savedAdvertiser.getAdvertiserContactName(),advertiser.getAdvertiserContactName());
        Assert.assertEquals(savedAdvertiser.getAdvertiserCreditLimit(),advertiser.getAdvertiserCreditLimit());
    }


    @Test
    public void findAllAdvertiser_whenGetMethodCalled_Return_AllAdvertiserList() {
        advertiserList = repository.findAll();
        Assert.assertTrue(advertiserList.size() > 0);
        Assert.assertEquals(advertiserList.size() , 2);
    }



    @Test
    public void updateAdvertiser_information_whenPutMethodCalled_Return_Advertiser_with_Updated_information() {
        advertiser.setAdvertiserContactName("Sam");
        Advertiser updatedAdvertiser = repository.save(advertiser);
        Assert.assertNotNull(updatedAdvertiser);
        Assert.assertEquals(updatedAdvertiser.getAdvertiserContactName() , "Sam");
    }

    @Test
    public void deleteAdvertiser_information_whenDeleteMethodCalled_Return_200_Status() {
        advertiserList = repository.findAll();
        repository.deleteById(advertiserList.get(0).getId());
        advertiserList = repository.findAll();
        Assert.assertEquals(advertiserList.size(),1);
    }

}