package org.ups.rfidtrack.utils;

import org.ups.rfidtrack.dto.ProductDto;
import org.ups.rfidtrack.dto.ProductDtoForCust;
import org.ups.rfidtrack.dto.ProductDtoForDesk;
import org.ups.rfidtrack.entity.Product;


public class ProductMapper {
    public static Product maptoProduct(ProductDto productDto){
        Product product=new Product();
        product.setAWB(productDto.getAWB());
        product.setDestination_data(productDto.getDestination_data());
        product.setDimensions(productDto.getDimensions());
        product.setLat(0.0);
        product.setLon(0.0);
        product.setOrigin_data(productDto.getOrigin_data());
        product.setProduct_details(productDto.getProduct_details());
        product.setReceiver_data(productDto.getReceiver_data());
        product.setReceiver_phone(productDto.getReceiver_phone());

        if(productDto.getRfid()!=null){
            product.setRfid(productDto.getRfid());
        }else {
            product.setRfid(null);
        }
        product.setStatus(StatusCodes.REGISTERED);
        product.setSender_data(productDto.getSender_data());
        product.setSender_phone(productDto.getSender_phone());

        return product;


    }


    public static ProductDtoForDesk maptoProductDtoForDesk(Product product) {
        return new ProductDtoForDesk(
                product.getOrigin_data(),
                product.getDestination_data(),
                product.getDimensions(),
                product.getSender_data(),
                product.getReceiver_data(),
                product.getSender_phone(),
                product.getReceiver_phone(),
                product.getProduct_details()
        );


    }

    public static ProductDto mapToProductDto(Product product){
        return new ProductDto(
                product.getAWB(),
                product.getOrigin_data(),
                product.getDestination_data(),
                product.getDimensions(),
                product.getSender_data(),
                product.getReceiver_data(),
                product.getSender_phone(),
                product.getReceiver_phone(),
                product.getProduct_details(),
                product.getRfid()
        );
    }

    public static Product mapToProduct(ProductDtoForCust productDtoForCust){
        return new Product(
                AWBclass.generateAWB(),
                productDtoForCust.getOrigin_data(),
                productDtoForCust.getDestination_data(),
                productDtoForCust.getDimensions(),
                productDtoForCust.getSender_data(),
                productDtoForCust.getReceiver_data(),
                productDtoForCust.getSender_phone(),
                productDtoForCust.getReceiver_phone(),
                productDtoForCust.getProduct_details(),
                null,
                null,
                null,
                0.0,
                0.0

        );
    }

}
