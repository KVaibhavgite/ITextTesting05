package com.example.itexttesting05;

import android.os.Environment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class PrintPDF {

    int invoiceNo;
    String name;
    String number;
    long invoiceDate;
    String item1;
    int itemQty1;
    int itemAmount1;
    String item2;
    int itemQty2;
    int itemAmount2;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public PrintPDF(int invoiceNo, String name, String number, long invoiceDate, String item1, int itemQty1, int itemAmount1, String item2, int itemQty2, int itemAmount2) {

        this.invoiceNo = invoiceNo;
        this.name = name;
        this.number = number;
        this.invoiceDate = invoiceDate;
        this.item1 = item1;
        this.itemQty1 = itemQty1;
        this.itemAmount1 = itemAmount1;
        this.item2 = item2;
        this.itemQty2 = itemQty2;
        this.itemAmount2 = itemAmount2;
    }



    public  void getPDF() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath,"InMyPdf.pdf");
        OutputStream stream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        float columnWidth[] = {120,220,120,100};
        Table table = new Table(columnWidth);

        table.addCell(new Cell().add(new Paragraph("Customer Name :")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(name)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Invoice No. :")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(invoiceNo+"")).setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add(new Paragraph("Mo. No.")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(number+"")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Date:")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(simpleDateFormat.format(invoiceDate))).setBorder(Border.NO_BORDER));

        float columnWidth2[] = {360,100,100};
        Table table1 = new Table(columnWidth2);

        table.addCell(new Cell().add(new Paragraph("Item Description")));
        table.addCell(new Cell().add(new Paragraph("Qty")));
        table.addCell(new Cell().add(new Paragraph("Amount")));

        table.addCell(new Cell().add(new Paragraph(item1)));
        table.addCell(new Cell().add(new Paragraph(itemQty1+"")));
        table.addCell(new Cell().add(new Paragraph(itemAmount1+"")));

        table.addCell(new Cell().add(new Paragraph(item2)));
        table.addCell(new Cell().add(new Paragraph(itemQty2+"")));
        table.addCell(new Cell().add(new Paragraph(itemAmount2+"")));

        table.addCell(new Cell(1,2).add(new Paragraph("")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(itemAmount1+itemAmount2))));

        document.add(table);
        document.add(new Paragraph("\n"));
        document.add(table1);
        document.close();




    }
}
