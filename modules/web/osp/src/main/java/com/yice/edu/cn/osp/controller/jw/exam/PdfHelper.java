package com.yice.edu.cn.osp.controller.jw.exam;

import com.evopdf.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfHelper {
    @Value("${evopdf.ip}")
    private String ip;
    @Value("${evopdf.port}")
    private int port;
    @Value("${evopdf.licenseKey}")
    private String licenseKey;
    @Value("${evopdf.password}")
    private String password;

    public byte[] htmlToPdf(String urlOrHtml, boolean isUrl) throws Exception {

        // create the HTML to PDF converter
        HtmlToPdfConverter htmlToPdfConverter = new HtmlToPdfConverter(ip, port);

        // set license key
        htmlToPdfConverter.setLicenseKey(licenseKey);

        // set service password if necessary
        htmlToPdfConverter.setServicePassword(password);

        // set HTML viewer width
        htmlToPdfConverter.setHtmlViewerWidth(1000);
        htmlToPdfConverter.setHtmlViewerHeight(842);

        // set navigation timeout
        //htmlToPdfConverter.setNavigationTimeout(navigationTimeout);

        // set conversion delay if necessary

        // set PDF page size
        htmlToPdfConverter.pdfDocumentOptions().setPdfPageSize(PdfPageSize.A4);
        htmlToPdfConverter.pdfDocumentOptions().setSinglePage(false);
//        htmlToPdfConverter.pdfDocumentOptions().setFitWidth(true);
//        htmlToPdfConverter.pdfDocumentOptions().setFitHeight(true);
//        htmlToPdfConverter.pdfDocumentOptions().setStretchToFit(true);

        htmlToPdfConverter.pdfViewerPreferences().setFitWindow(true);

        // set PDF page orientation
        htmlToPdfConverter.pdfDocumentOptions().setPdfPageOrientation(PdfPageOrientation.Portrait);

        // set margins

        htmlToPdfConverter.pdfDocumentOptions().setLeftMargin(0);

        htmlToPdfConverter.pdfDocumentOptions().setRightMargin(0);

        htmlToPdfConverter.pdfDocumentOptions().setTopMargin(0);

        htmlToPdfConverter.pdfDocumentOptions().setBottomMargin(0);

        // add header
//        htmlToPdfConverter.pdfDocumentOptions().setShowHeader(true);
//        drawHeader(htmlToPdfConverter, true);

        // add footer
//        htmlToPdfConverter.pdfDocumentOptions().setShowFooter(true);
//        drawFooter(htmlToPdfConverter, true, true);

        byte[] outPdfBuffer = null;

        if (isUrl) {
            // convert URL to PDF

            outPdfBuffer = htmlToPdfConverter.convertUrl(urlOrHtml);
        } else {
            // convert HTML to PDF

            outPdfBuffer = htmlToPdfConverter.convertHtml(urlOrHtml, null);//第二个参数baseUrl是资源路径
        }

        return outPdfBuffer;
    }

    public byte[] multiHtmlToPdf(List<String> htmls) throws Exception {
        com.evopdf.Document pdfDocument = new com.evopdf.Document(ip, port);
        pdfDocument.setLicenseKey(licenseKey);
        pdfDocument.setServicePassword(password);
        for (String html : htmls) {
            PdfPage pdfPage = pdfDocument.addPage(PdfPageSize.A3, null, PdfPageOrientation.Landscape);
            HtmlToPdfElement element = new HtmlToPdfElement(0, 0, 0, html, null, 1191);
            pdfPage.addElement(element);
        }
        return pdfDocument.save();
    }

    //竖向A4
    public byte[] multiHtmpToPdfA4(List<String> htmls) throws Exception{
        com.evopdf.Document pdfDocument = new com.evopdf.Document(ip,port);
        pdfDocument.setLicenseKey(licenseKey);
        pdfDocument.setServicePassword(password);
        for(String html:htmls){
            PdfPage pdfPage = pdfDocument.addPage(PdfPageSize.A4,null,PdfPageOrientation.Portrait);
            HtmlToPdfElement element = new HtmlToPdfElement(0,0,0,html,null,842);
            pdfPage.addElement(element);
        }
        return pdfDocument.save();
    }


    private void drawHeader(HtmlToPdfConverter htmlToPdfConverter, boolean drawHeaderLine) {
        String headerHtmlString =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<title>HTML in Header</title>" +
                        "</head>" +
                        "<body style=\"font-family: 'Times New Roman'; font-size: 14px\">" +
                        "<table>" +
                        "<tr>" +
                        "<td style=\"font-weight: bold; color: navy\">HTML in Header</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td>" +
                        "<a href=\"http://www.evopdf.com\">" +
                        "<img alt=\"Logo Image\" style=\"width: 200px\" src=\"http://www.evopdf.com/images/evopdf_logo.jpg\" /></a>" +
                        "</td>" +
                        "</tr>" +
                        "</table>" +
                        "</body>" +
                        "</html>";

        // Set the header height in points
        htmlToPdfConverter.pdfHeaderOptions().setHeaderHeight(60);

        // Set header background color
        htmlToPdfConverter.pdfHeaderOptions().setHeaderBackColor(RgbColor.WHITE);

        // Create a HTML element to be added in header
        HtmlToPdfElement headerHtml = new HtmlToPdfElement(headerHtmlString, null);

        // Set the HTML element to fit the container height
        headerHtml.setFitHeight(true);

        // Add HTML element to header
        htmlToPdfConverter.pdfHeaderOptions().addElement(headerHtml);

        if (drawHeaderLine) {
            // Calculate the header width based on PDF page size and margins
            boolean portraitOrientation = htmlToPdfConverter.pdfDocumentOptions().pdfPageOrientation() == PdfPageOrientation.Portrait;
            float headerWidth = (portraitOrientation ? htmlToPdfConverter.pdfDocumentOptions().pdfPageSize().width() : htmlToPdfConverter.pdfDocumentOptions().pdfPageSize().height())
                    - htmlToPdfConverter.pdfDocumentOptions().leftMargin()
                    - htmlToPdfConverter.pdfDocumentOptions().rightMargin();

            // Calculate header height
            float headerHeight = htmlToPdfConverter.pdfHeaderOptions().headerHeight();

            // Create a line element for the bottom of the header
            LineElement headerLine = new LineElement(0, headerHeight - 1, headerWidth, headerHeight - 1);

            // Set line color
            headerLine.setForeColor(RgbColor.GRAY);

            // Add line element to the bottom of the header
            htmlToPdfConverter.pdfHeaderOptions().addElement(headerLine);
        }


    }

    private void drawFooter(HtmlToPdfConverter htmlToPdfConverter, boolean addPageNumbers, boolean drawFooterLine) {
        String footerHtmlString =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "<title>HTML in Footer</title>" +
                        "</head>" +
                        "<body style=\"font-family: 'Times New Roman'; font-size: 14px\">" +
                        "<table>" +
                        "<tr>" +
                        "<td style=\"font-weight: bold; color: green\">HTML in Footer</td>" +
                        "</tr>" +
                        "<tr>" +
                        "<td>" +
                        "<a href=\"http://www.evopdf.com\">" +
                        "<img alt=\"Logo Image\" style=\"width: 200px\" src=\"http://www.evopdf.com/images/evopdf_logo.jpg\" /></a>" +
                        "</td>" +
                        "</tr>" +
                        "</table>" +
                        "</body>" +
                        "</html>";

        // Set the footer height in points
        htmlToPdfConverter.pdfFooterOptions().setFooterHeight(60);

        // Set footer background color
        htmlToPdfConverter.pdfFooterOptions().setFooterBackColor(RgbColor.WHITE_SMOKE);

        // Create a HTML element to be added in footer
        HtmlToPdfElement footerHtml = new HtmlToPdfElement(footerHtmlString, null);

        // Set the HTML element to fit the container height
        footerHtml.setFitHeight(true);

        // Add HTML element to footer
        htmlToPdfConverter.pdfFooterOptions().addElement(footerHtml);

        // Add page numbering
        if (addPageNumbers) {
            // Create a text element with page numbering place holders &p; and &P;
            TextElement footerText = new TextElement(0, 30, "Page &p; of &P;  ",
                    new PdfFont("Times New Roman", 10, true));

            // Align the text at the right of the footer
            footerText.setTextAlign(HorizontalTextAlign.Right);

            // Set page numbering text color
            footerText.setForeColor(RgbColor.NAVY);

            // Embed the text element font in PDF
            footerText.setEmbedSysFont(true);

            // Add the text element to footer
            htmlToPdfConverter.pdfFooterOptions().addElement(footerText);
        }

        if (drawFooterLine) {
            // Calculate the footer width based on PDF page size and margins
            boolean portraitOrientation = htmlToPdfConverter.pdfDocumentOptions().pdfPageOrientation() == PdfPageOrientation.Portrait;
            float footerWidth = (portraitOrientation ? htmlToPdfConverter.pdfDocumentOptions().pdfPageSize().width() : htmlToPdfConverter.pdfDocumentOptions().pdfPageSize().height())
                    - htmlToPdfConverter.pdfDocumentOptions().leftMargin()
                    - htmlToPdfConverter.pdfDocumentOptions().rightMargin();

            // Create a line element for the top of the footer
            LineElement footerLine = new LineElement(0, 0, footerWidth, 0);

            // Set line color
            footerLine.setForeColor(RgbColor.GRAY);

            // Add line element to the bottom of the footer
            htmlToPdfConverter.pdfFooterOptions().addElement(footerLine);
        }

    }

}
