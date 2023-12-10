package org.ups.rfidtrack.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ups.rfidtrack.entity.Product;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product,String> {


    @Query(value = "update product set lat=?2 , lon=?3 where rfid=?1",nativeQuery = true)
    @Transactional
    @Modifying
    void updateLatLon(String rfid,double  lat,double lon);


    @Query(value = "update product set current_hub=?2 , status=?3 where rfid=?1",nativeQuery = true)
    @Transactional
    @Modifying
    void updateCurrentLocation(String rfid, String current_hub,String status);


    @Query(value = "update product set rfid=?2 where awb=?1",nativeQuery = true)
    @Transactional
    @Modifying
    void linkRfid(String awb,String rfid);

    @Query(value = "update product set status=?2 where rfid=?1",nativeQuery = true)
    @Transactional
    @Modifying
    void updateStatus(String rfid, String status);



    @Query(value = "update product set lat=?1,lon=?2 where status=?3",nativeQuery = true)
    @Transactional
    @Modifying
    void updateGeo(double lat, double lon,String status);



    @Query(value = "select * from product where status=?1",nativeQuery = true)
    List<Product> getOFD(String status);


}
