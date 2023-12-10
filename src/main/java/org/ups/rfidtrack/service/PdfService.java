package org.ups.rfidtrack.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.parser.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.ups.rfidtrack.dto.ProductDto;
import org.ups.rfidtrack.dto.ProductDtoForCust;
import org.ups.rfidtrack.entity.Product;
import org.ups.rfidtrack.utils.ProductMapper;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;


@Service
public class PdfService {

    @Autowired
    ProductService productService;

    public ResponseEntity<InputStreamResource> generatePdf(String awb) throws Exception{

        ProductDto productDto= ProductMapper.mapToProductDto(productService.getProductByAwb(awb));

        File file=new File(FileSystemView.getFileSystemView().getHomeDirectory().getPath()+"/shipping_label.pdf");
        boolean isFileCreated=file.createNewFile();
        Document rfidDocument = new Document();

        InputStreamResource streamResource=new InputStreamResource(new FileInputStream(file));

        try {

            //creating document with no of pages, size and margin
            rfidDocument.setPageSize(PageSize.A4.rotate());
            rfidDocument.setPageCount(1);
            rfidDocument.setMargins(15,15,15,15);
            PdfWriter pdfWriter=PdfWriter.getInstance(rfidDocument,new FileOutputStream(file));
//            HeaderFooterPageEvent event=new HeaderFooterPageEvent();
//            pdfWriter.setPageEvent(event);
//            pdfWriter.addPageDictEntry(PdfName.ROTATE,PdfPage.LANDSCAPE);

            //opening the document for editing
            rfidDocument.open();



            //setting properties for the pdf
            rfidDocument.addAuthor("ups");
            rfidDocument.addTitle("Shipping tag");
            rfidDocument.addSubject("tag details");


            PdfPTable mainTable=new PdfPTable(new float[]{1,1});
            mainTable.setWidthPercentage(100);


            //right side page
            String image="src/main/resources/images/ViewPrint_right.png";
            Image image1=Image.getInstance(image);
            image1.setRotationDegrees(90);
            System.out.println(image1.getScaledHeight()+" "+image1.getScaledWidth());
            PdfPCell mainCell2=new PdfPCell(image1,true);

            //left side page
            PdfPCell mainCell1=new PdfPCell();
            mainCell1.setPadding(20);



            //copy paste




            PdfPTable headerTable=new PdfPTable(1);
            headerTable.setWidthPercentage(100);
            headerTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            String img =  "src/main/resources/images/ups_logo.png";
            Image imageTop=Image.getInstance(img);
            imageTop.scaleToFit(30,30);;
            PdfPCell imageCell=new PdfPCell(imageTop);
            imageCell.setBorder(Rectangle.NO_BORDER);
            imageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            headerTable.addCell(imageCell);



            //font for tittles
            Font ft=new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD,BaseColor.BLACK);

            //font for small text
            Font fs=new Font(Font.FontFamily.HELVETICA,7,Font.BOLD,BaseColor.BLACK);

            //topic table
            PdfPTable senderReceiverTopicTable = new PdfPTable(2);
            senderReceiverTopicTable.setWidthPercentage(100);
            Paragraph st1 = new Paragraph("Sender :",ft);
            Paragraph st2 = new Paragraph("Receiver :",ft);

            PdfPCell cst2=new PdfPCell(st2);
            cst2.setBorder(Rectangle.NO_BORDER);
            cst2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cst1=new PdfPCell(st1);
            cst1.setBorder(Rectangle.NO_BORDER);
            cst1.setHorizontalAlignment(Element.ALIGN_CENTER);
            senderReceiverTopicTable.addCell(new PdfPCell(cst1));
            senderReceiverTopicTable.addCell(new PdfPCell(cst2));

            //sender receiver details table
            PdfPTable senderReceiverTable = new PdfPTable(2);
            senderReceiverTable.setWidthPercentage(100);
            Paragraph s1 = new Paragraph(productDto.getSender_data()+",\n"+productDto.getOrigin_data()+",\nPh: "+productDto.getSender_phone());
            Paragraph s2 = new Paragraph(productDto.getReceiver_data()+",\n"+productDto.getDestination_data()+",\nPh: "+productDto.getReceiver_phone());
            PdfPCell cs2=new PdfPCell(s2);
            cs2.setBorder(Rectangle.NO_BORDER);
            cs2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell cs1=new PdfPCell(s1);
            cs1.setBorder(Rectangle.NO_BORDER);
            cs1.setHorizontalAlignment(Element.ALIGN_CENTER);
            senderReceiverTable.addCell(new PdfPCell(cs1));
            senderReceiverTable.addCell(new PdfPCell(cs2));

            //creating a table for QR code
            PdfPTable QRtable=new PdfPTable(2);
            QRtable.setWidthPercentage(100);
            QRtable.setHorizontalAlignment(Element.ALIGN_LEFT);

//            Image qrcodeImage=Image.getInstance(FileSystemView.getFileSystemView().getHomeDirectory().getPath()+"/qrcode.png");
            Image qrcodeImage=Image.getInstance(generateQrcode(productDto.getAWB()));
            PdfPCell qrcodeCell=new PdfPCell(qrcodeImage);
            qrcodeCell.setBorder(Rectangle.NO_BORDER);
            qrcodeCell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);



            Paragraph qrcodeRightText=new Paragraph(
                    """
                    UPS offers a wide range of services, including
                      package delivery, freight shipping, logistics,
                      and supply chain solutions for businesses and
                      individuals.
                    UPS is known for its advanced package tracking
                      system, allowing customers to track the
                      real-time location and status of their
                      shipments through the UPS website or app.
                    UPS operates one of the world's largest cargo
                      airlines, with a fleet of over 250 aircraft,
                      including both large and small cargo planes.
                    UPS provides a network of Access Point
                      locations where customers can drop off or
                      pick up packages, making it convenient for
                      recipients.
                    UPS has been at the forefront of using technology
                      to optimize its operations, including route
                      optimization algorithms, GPS tracking, and
                      automation in sorting and distribution centers.
                    UPS is a well-established company that plays a
                      crucial role in the global movement of packages
                      and goods, making it an integral part of the supply
                      chain for businesses and consumers alike.
                    """,
                    fs
            );
            qrcodeRightText.setAlignment(Element.ALIGN_LEFT);

            PdfPCell qrcodeRightTextCell=new PdfPCell(qrcodeRightText);
            qrcodeRightTextCell.setBorder(Rectangle.NO_BORDER);
//            qrcodeRightTextCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            qrcodeRightTextCell.setVerticalAlignment(Element.ALIGN_CENTER);


            QRtable.addCell(qrcodeCell);
            QRtable.addCell(qrcodeRightTextCell);



            //Barcode image
            PdfPTable barcodeTable=new PdfPTable(1);
            barcodeTable.setWidthPercentage(100);
            barcodeTable.setHorizontalAlignment(Element.ALIGN_CENTER);

//            Image barcodeImage=Image.getInstance(FileSystemView.getFileSystemView().getHomeDirectory().getPath()+"/barcode.png");
            Image barcodeImage=Image.getInstance(generateBarcode(productDto.getAWB()));
            PdfPCell barcodeCell=new PdfPCell(barcodeImage,true);
            barcodeCell.setBorder(Rectangle.NO_BORDER);
            barcodeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            barcodeTable.addCell(barcodeCell);


            //barcode text
            PdfPTable barcodeTextTable=new PdfPTable(1);
            barcodeTextTable.setWidthPercentage(100);
            barcodeTextTable.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph barcodeText=new Paragraph(productDto.getAWB(),ft);
            barcodeText.setAlignment(Element.ALIGN_CENTER);
//            barcodeText.setIndentationRight(20);

            PdfPCell barcodeTextCell=new PdfPCell(barcodeText);
            barcodeTextCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            barcodeTextCell.setBorder(Rectangle.NO_BORDER);
            barcodeTextTable.addCell(barcodeTextCell);


            //small-time
            PdfPTable smallTimeTable=new PdfPTable(1);
            smallTimeTable.setWidthPercentage(100);
            smallTimeTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            Font smallTextFont=new Font(Font.FontFamily.COURIER,5,Font.BOLDITALIC,BaseColor.BLACK);
            Paragraph smallTime=new Paragraph(new Date().toString(),smallTextFont);

            PdfPCell cell1=new PdfPCell(smallTime);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            smallTimeTable.addCell(cell1);

            //small-text
            PdfPTable smallTextTable=new PdfPTable(1);
            smallTextTable.setWidthPercentage(100);
            smallTextTable.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph smallText=new Paragraph("ups shipping label",smallTextFont);
            PdfPCell cell=new PdfPCell(smallText);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            smallTextTable.addCell(cell);

            Font footerTextFont=new Font(Font.FontFamily.COURIER,4,Font.BOLD,BaseColor.BLACK);

            Paragraph footerText=new Paragraph(
                    productDto.toString(),
                    footerTextFont);
            System.out.println(productDto);
            footerText.setAlignment(Element.ALIGN_LEFT);









            mainCell1.addElement(headerTable);
            mainCell1.addElement(senderReceiverTopicTable);
            mainCell1.addElement(senderReceiverTable);
            mainCell1.addElement(new Paragraph("\n"));
            mainCell1.addElement(new LineSeparator());
            mainCell1.addElement(smallTextTable);
            mainCell1.addElement(new Paragraph("\n"));
            mainCell1.addElement(QRtable);
            mainCell1.addElement(new Paragraph("\n"));
            mainCell1.addElement(new LineSeparator());
            mainCell1.addElement(new Paragraph("\n"));
            mainCell1.addElement(barcodeTable);
            mainCell1.addElement(barcodeTextTable);
            mainCell1.addElement(new Paragraph("\n"));
            mainCell1.addElement(new LineSeparator());
            mainCell1.addElement(smallTimeTable);
            mainCell1.addElement(footerText);

            //end




            mainTable.addCell(mainCell1);
            mainTable.addCell(mainCell2);


            rfidDocument.add(mainTable);

            //closing the document after editing
            rfidDocument.close();


            streamResource=new InputStreamResource(new FileInputStream(file));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }





        return ResponseEntity.ok()
                .contentLength(file.length())
                .header("Content-Disposition","inline; filename="+"tagDocument")
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(streamResource);
    }


    public String generateQrcode(String awb) throws WriterException, IOException {

        String qrcodePngPath=FileSystemView.getFileSystemView().getHomeDirectory()+"/qrcode.png";

        BitMatrix matrix=new MultiFormatWriter().encode(
                awb,
                BarcodeFormat.QR_CODE,
                140,
                140
        );
        MatrixToImageWriter.writeToPath(
                matrix,
                "png",
                Paths.get(FileSystemView.getFileSystemView().getHomeDirectory().getPath(),"qrcode.png")
        );

        return qrcodePngPath;

    }


    public String generateBarcode(String awbNumber) throws WriterException,IOException{
        String barcodePngPath=FileSystemView.getFileSystemView().getHomeDirectory().getPath()+"/barcode.png";
        BitMatrix matrix=new MultiFormatWriter().encode(
                "*"+awbNumber+"*",
                BarcodeFormat.CODE_39,
                100,
                50
        );

        MatrixToImageWriter.writeToPath(
                matrix,
                "png",
                Paths.get(FileSystemView.getFileSystemView().getHomeDirectory().getPath(),"barcode.png")
        );

        return barcodePngPath;

    }

    public String registerProduct(ProductDtoForCust productDtoForCust) {
        return productService.registerProduct(ProductMapper.mapToProduct(productDtoForCust));
    }
}
