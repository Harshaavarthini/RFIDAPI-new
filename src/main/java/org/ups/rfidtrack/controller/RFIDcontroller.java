package org.ups.rfidtrack.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ups.rfidtrack.bodies.LocBody;
import org.ups.rfidtrack.bodies.RfidBody;
import org.ups.rfidtrack.dto.ProductDto;
import org.ups.rfidtrack.dto.ProductDtoForCust;
import org.ups.rfidtrack.dto.ProductDtoForDesk;
import org.ups.rfidtrack.entity.Product;
import org.ups.rfidtrack.service.PdfService;
import org.ups.rfidtrack.service.ProductService;
import java.util.List;


@RestController
@RequestMapping("rfid")
@CrossOrigin("*")
public class RFIDcontroller {

    @Autowired
    ProductService productService;

    @Autowired
    PdfService pdfService;


    @PostMapping("store")
    public String storeProduct(@RequestBody ProductDto productDto){
        System.out.println(productDto);
        return productService.saveProduct(productDto);
    }

    @GetMapping("getprod")
    public Product getProduct(@RequestParam("awb") String awb){
        return productService.getProductByAwb(awb);
    }

    @PostMapping("updatestatus")
    public String updateStatus(@RequestParam String rfid,@RequestParam String status){
        return productService.updateProductStatus(rfid, status);
    }

    @GetMapping("getall")
    public List<Product> getAll(){
        return productService.getAllProd();
    }


    @GetMapping("getstat/{awb}")
    public String getStatus(@PathVariable String awb){
        return productService.getStatus(awb);
    }

    @PostMapping("updateloc")
    public String updateLocation(@RequestParam String rfid,@RequestParam double lat,@RequestParam double lon){
        return productService.updateLatLon(rfid,lat,lon);

    }

    @PostMapping("updateCurrentHub")
    public String updateCurrentLocation(@RequestParam String rfid, @RequestParam String current_hub,@RequestParam String status){
        return productService.updateCurrentLocation(rfid, current_hub, status);
    }

//    @GetMapping("getloc/{awb}")
//    public LocBody getLoc(@PathVariable String awb){
//        return productService.getLatLong(awb);
//    }

    @GetMapping("getdetails/{awb}")
    public ProductDtoForDesk getDetails(@PathVariable String awb){
        return productService.getDetails(awb);
    }

    @PostMapping("linkrfid")
    public String linkRfid(@RequestBody RfidBody body){
        return productService.linkRfid(body);
    }


    @GetMapping("reset")
    public String resetDatas(){
        return productService.resetDetails();
    }

    @PostMapping("registerproduct")
    public String generateLabelPdf(@RequestBody ProductDtoForCust productDtoForCust) throws Exception {
        System.out.println("avan tharathu : "+productDtoForCust.getReceiver_phone());
        return pdfService.registerProduct(productDtoForCust);
    }

    @GetMapping("getlabel/{awb}")
    public ResponseEntity<InputStreamResource> getLabelPdf(@PathVariable String awb) throws Exception {

        return pdfService.generatePdf(awb);

    }


    @PostMapping("updategeo")
    public String updateGeo(double lat,double lon){
        return productService.updateGeo(lat,lon);
    }


    @GetMapping("getloc")
    public LocBody getLoc(){
        return productService.getLatLong();
    }

    @GetMapping("resetwithrfid")
    public String resetWithRfid(){
        return productService.resetWithRfid();
    }



}
