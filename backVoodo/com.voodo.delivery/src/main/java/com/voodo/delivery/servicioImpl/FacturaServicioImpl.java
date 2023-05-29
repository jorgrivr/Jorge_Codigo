package com.voodo.delivery.servicioImpl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.voodo.delivery.JWT.JwtFilter;
import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.dao.FacturaDao;
import com.voodo.delivery.entidad.Factura;
import com.voodo.delivery.servicio.FacturaServicio;
import com.voodo.delivery.utils.VoodoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class FacturaServicioImpl implements FacturaServicio {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    FacturaDao facturaDao;


    @Override
    public ResponseEntity<String> generarReporte(Map<String, Object> requestMap) {
        log.info("Dentro generar reporte");
        try{
            String fileName;
            if (validateRequestMap(requestMap)){
                if(requestMap.containsKey("creadoPor") && !(Boolean)requestMap.get("creadoPor")){
                    fileName=(String) requestMap.get("uuid");
                }else {
                    fileName= VoodoUtils.getUUID();
                    requestMap.put("uuid",fileName);
                    insertarFactura(requestMap);

                }

                String data="Nombre: "+requestMap.get("nombre")+"\n"
                        + "Numero Contacto: "+ requestMap.get("numero_contacto")+ "\n"
                        +"Email: "+requestMap.get("email")+"\n"
                        + "Metodo Pago: " + requestMap.get("metodoPago");

                Document document= new Document();
                PdfWriter.getInstance(document,new FileOutputStream(Constantes.STORE_LOCATION+"//"+fileName+".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph chunk= new Paragraph("Voodo Delivery",getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph= new Paragraph(data +"\n \n", getFont("Data")) ;
                document.add(paragraph);

                PdfPTable table=new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray=VoodoUtils.getJsonArrayFromString((String) requestMap.get("detallesProducto"));
                for(int i=0; i<jsonArray.length(); i++){
                    addRows(table,VoodoUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer=new Paragraph("Total: " + requestMap.get("total") +"\n"
                        +"Gracias por su visita,vuevla pronto!!",getFont("Data"));
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuid\":\""+ fileName+"\"}",HttpStatus.OK);


            }
            return VoodoUtils.getResponseEntity("Required data not found.",HttpStatus.BAD_REQUEST);




        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Dentro  addRows");
        table.addCell((String) data.get("nombre"));
        table.addCell((String) data.get("categoria"));
        table.addCell((String) data.get("cantidad"));
        table.addCell(Double.toString((Double)data.get("precio")));
        table.addCell(Double.toString((Double)data.get("total")));

    }

    private void addTableHeader(PdfPTable table) {
        log.info("Dentro addTableHeader");
        Stream.of("Nombre","Categoria","Cantidad","Precio","Total")
                .forEach(columnTitle->{
                    PdfPCell header=new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBorderColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private Font getFont(String type) {
        log.info("Dentro getFont");
        switch (type){
            case "Header":
                Font headerFont= FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;

            case "Data":
                Font dataFont= FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;

            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Dentro setRectangleInPdf");
        Rectangle rect= new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBackgroundColor(BaseColor.WHITE);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private void insertarFactura(Map<String, Object> requestMap) {
        try {
            Factura factura=new Factura();
            factura.setUuid((String) requestMap.get("uuid"));
            factura.setNombre((String)requestMap.get("nombre"));
            factura.setEmail((String)requestMap.get("email"));
            factura.setNumero_contacto((String)requestMap.get("numero_contacto"));
            factura.setMetodoPago((String)requestMap.get("metodoPago"));
            factura.setTotal(Integer.parseInt((String)requestMap.get("total")));
            factura.setDetallesProducto((String)requestMap.get("detallesProducto"));
            factura.setCreadoPor(jwtFilter.getCurrentUser());
            facturaDao.save(factura);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("nombre")
                && requestMap.containsKey("numero_contacto")
                && requestMap.containsKey("email")
                && requestMap.containsKey("metodoPago")
                && requestMap.containsKey("detallesProducto")
                && requestMap.containsKey("total");

    }

    @Override
    public ResponseEntity<List<Factura>> getFactura() {
        List<Factura> list=new ArrayList<>();
        if (jwtFilter.isAdmin()){
            list=facturaDao.getAllFactura();
        }else {
            list=facturaDao.getFacturaByUserName(jwtFilter.getCurrentUser());

        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Dentro getPdf: requestMap{}",requestMap);
        try{
            byte[] byteArray=new byte[0];
            if (!requestMap.containsKey("uuid") && validateRequestMap((requestMap)))
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);

            String filePath=Constantes.STORE_LOCATION+"\\"+(String) requestMap.get("uuid")+".pdf";
            if (VoodoUtils.isFileExist(filePath)){
                byteArray=getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }else {
                requestMap.put("creadoPor",false);
                generarReporte(requestMap);
                byteArray=getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    private byte[] getByteArray(String filePath) throws IOException {
        File initialFile= new File(filePath);
        InputStream targetStream= new FileInputStream(filePath);
        byte[] byteArray= IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deletePdf(Integer id) {
        try {
            Optional optional=facturaDao.findById(id);
            if (!optional.isEmpty()){
                facturaDao.deleteById(id);
                return VoodoUtils.getResponseEntity("id Factura borrada correctamente",HttpStatus.OK);

            }else{
                return VoodoUtils.getResponseEntity("id Factura no existe",HttpStatus.OK);

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
