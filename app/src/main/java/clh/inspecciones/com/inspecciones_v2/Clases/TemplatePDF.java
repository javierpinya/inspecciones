package clh.inspecciones.com.inspecciones_v2.Clases;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import clh.inspecciones.com.inspecciones_v2.Activities.ViewPDFActivity;

public class TemplatePDF {

    private Context context;
    private String nombrePDF;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubtitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);

    public TemplatePDF(Context context, String nombrePDF) {
        this.context = context;
        this.nombrePDF = nombrePDF;
    }


    public void openDocument() {
        createFile();
        try {
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();


        } catch (Exception e) {
            Log.e("OpenDocument", e.toString());
        }
    }

    private void createFile() {
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        pdfFile = new File(folder, nombrePDF + ".pdf");
    }

    public void closeDocuemnt() {
        document.close();
    }

    public void addMetaData(String title, String subject, String author) {
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitles(String title, String subTitle, String date) {

        try {
            paragraph = new Paragraph();
            addChildP(new Paragraph(title, fTitle));
            addChildP(new Paragraph(subTitle, fSubtitle));
            addChildP(new Paragraph("Generado: " + date, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (DocumentException e) {
            Log.e("addTitles ", e.toString());
        }
    }

    public void addParagraph(String text) {
        try {
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingBefore(5);
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        } catch (DocumentException e) {
            Log.e("addParagraph ", e.toString());
        }
    }

    public void createTable(String[] header, ArrayList<String[]> clients) {
        try {
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC < header.length) {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubtitle));
                pdfPCell.setBackgroundColor(BaseColor.GREEN);
                pdfPTable.addCell(pdfPCell);
            }

            for (int indexR = 0; indexR < clients.size(); indexR++) {
                String[] row = clients.get(indexR);
                for (indexC = 0; indexC < clients.size(); indexC++) {
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(30);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (DocumentException e) {
            Log.e("createTable ", e.toString());
        }
    }

    public void addSpacing10() {
        try {
            paragraph = new Paragraph();
            paragraph.setSpacingBefore(10);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);
        } catch (DocumentException e) {
            Log.e("addSpacing", e.toString());
        }
    }

    private void addChildP(Paragraph childParagraph) {
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void viewPDF() {
        Intent intent = new Intent(context, ViewPDFActivity.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
