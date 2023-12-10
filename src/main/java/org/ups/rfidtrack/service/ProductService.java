package org.ups.rfidtrack.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ups.rfidtrack.bodies.LocBody;
import org.ups.rfidtrack.bodies.RfidBody;
import org.ups.rfidtrack.dto.ProductDto;
import org.ups.rfidtrack.dto.ProductDtoForDesk;
import org.ups.rfidtrack.entity.Product;
import org.ups.rfidtrack.exceptions.ProductNotFoundException;
import org.ups.rfidtrack.repo.ProductRepo;
import org.ups.rfidtrack.utils.AWBclass;
import org.ups.rfidtrack.utils.Dummies;
import org.ups.rfidtrack.utils.ProductMapper;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;


    public String saveProduct(ProductDto productDto){
        Product product= ProductMapper.maptoProduct(productDto);
        product.setAWB(productDto.getAWB());
        System.out.println(product.getAWB());
        productRepo.save(product);
        return "successfully stored";

    }

    public Product getProductByAwb(String awb) {
        Optional<Product> product=productRepo.findById(awb);
        if(product.isPresent()){
            return product.get();
        }else{
            throw new ProductNotFoundException("No product found");
        }
    }

    public String updateProductStatus(String rfid, String status) {
        productRepo.updateStatus(rfid,status);
        return "updated";
    }

    public List<Product> getAllProd(){
        return productRepo.findAll().stream().filter(p->
            p.getRfid()!=null
        ).collect(Collectors.toList());
    }

    public String updateLatLon(String rfid,double lat,double lon){
        productRepo.updateLatLon(rfid,lat,lon);
        return "location updated";
    }

//    public LocBody getLatLong(String awb){
//        double[] latlon=new double[2];
//        Optional<Product> product=productRepo.findById(awb);
//        if(product.isPresent()){
//            latlon[0]=product.get().getLat();
//            latlon[1]=product.get().getLon();
//        }else{
//            throw new ProductNotFoundException("No Such Product");
//        }
//
//        return new LocBody(latlon[0],latlon[1]);
//
//    }

    public String getStatus(String awb) {
        String status;
        Optional<Product> product=productRepo.findById(awb);
        if(product.isPresent()){
            status=product.get().getStatus();
        }else{
            throw new ProductNotFoundException("No Such Product");
        }
        return status;
    }

    public String updateCurrentLocation(String rfid, String current_hub, String status) {
        productRepo.updateCurrentLocation(rfid, current_hub,status);
        return "Current Location updated";
    }

    public ProductDtoForDesk getDetails(String awb) {
        ProductDtoForDesk productDtoForDesk;
        Optional<Product> product=productRepo.findById(awb);
        if(product.isPresent()){
            productDtoForDesk=ProductMapper.maptoProductDtoForDesk(product.get());
        }else{
            throw new ProductNotFoundException("No Such product");
        }
        return productDtoForDesk;
    }

    public String linkRfid(RfidBody body) {
        productRepo.linkRfid(body.getAwb(),body.getRfid());
        return "linked successfully";
    }


    public String resetWithRfid(){
//        productRepo.deleteAll();
        List<Product> products=getDummiesWithRfid();
        productRepo.saveAll(products);
        return "reset done with rfid";
    }



    public String resetDetails(){
        productRepo.deleteAll();
        List<Product> products=getDummies();
        productRepo.saveAll(products);
        return "reset done";
    }


    private List<Product> getDummies(){
        List<Product> products=new ArrayList<>();
        Random random=new Random();
        for(int i=0;i<10;i++){
            ProductDto proDto=new ProductDto(
                    Dummies.dummyAWBS[i],
                    "Chennai",
                    "Atlanta",
                    Dummies.dummyDims[random.nextInt(0,11)],
                    Dummies.dummyNames[random.nextInt(0,20)],
                    Dummies.dummyNames[random.nextInt(0,20)],
                    Dummies.dummyPhones[random.nextInt(0,11)],
                    Dummies.dummyPhones[random.nextInt(0,11)],
                    Dummies.dummyProds[random.nextInt(0,3)],
                    null
            );
            products.add(ProductMapper.maptoProduct(proDto));
        }

        return products;

    }

    public List<Product> getDummiesWithRfid(){

        List<Product> productsDummiesWithRfid=new ArrayList<>();

        Random random=new Random();
        for(int i=0;i<6;i++){
            ProductDto proDto=new ProductDto(
                    AWBclass.generateAWB(),
                    "Chennai",
                    "Atlanta",
                    Dummies.dummyDims[random.nextInt(0,11)],
                    Dummies.dummyNames[random.nextInt(0,20)],
                    Dummies.dummyNames[random.nextInt(0,20)],
                    Dummies.dummyPhones[random.nextInt(0,11)],
                    Dummies.dummyPhones[random.nextInt(0,11)],
                    Dummies.dummyProds[random.nextInt(0,3)],
                    Dummies.dummyRfids[i]
            );
            productsDummiesWithRfid.add(ProductMapper.maptoProduct(proDto));

        }


        return productsDummiesWithRfid;
    }

    public String registerProduct(Product product){
        productRepo.save(product);
        return product.getAWB();
    }


    public String updateGeo(double lat,double lon) {
        productRepo.updateGeo(lat,lon,"OUT FOR DELIVERY");
        return "geo updated";
    }


    public LocBody getLatLong(){
        double[] latlon=new double[2];

        List<Product> products=productRepo.getOFD("OUT FOR DELIVERY");
        Product p=products.get(0);
        latlon[0]=p.getLat();
        latlon[1]=p.getLon();
        return new LocBody(latlon[0],latlon[1]);

    }




}
