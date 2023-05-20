package com.example.stockmanagement.Model.Classes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.example.stockmanagement.Model.Database.ClientsDB;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Pdf {
    public static void creatReceipt(Order order) throws SQLException {
        ClientsDB cdb = new ClientsDB();
        String fileName = "/home/ayoub/Documents/Receipts/Receipt_" + order.getOid() + "_" + cdb.getClientName(order.getClientId()) +".pdf";

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Paragraph p = new Paragraph("Receipt");
            p.setAlignment(Paragraph.ALIGN_CENTER);
            Font font = new Font();
            font.setStyle(Font.BOLD);
            font.setSize(20);
            p.setFont(font);
            document.add(p);
            // document.add(new Paragraph("Receipt"));
            document.add(new Paragraph("Order ID: " + order.getOid()));
            document.add(new Paragraph("Client: " + cdb.getClientName(order.getClientId())));
            document.add(new Paragraph("Order Date: " + order.getDate()));
            for (Product product : order.getProductsList().keySet()) {
                document.add(new Paragraph(product.getName() + " " + product.getPrice() + " DH x " + order.getProductsList().get(product)));
            }
            document.add(new Paragraph("Total: " + order.getTotal() + " DH"));
            document.close();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }
}
