package ru.doublebyte.arbirter;

import org.eclipse.birt.report.engine.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.doublebyte.arbirter.types.RenderRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ReportRenderer {

    private static final Logger logger = LoggerFactory.getLogger(ReportRenderer.class);

    @Autowired
    private BirtEngine engine;

    /**
     * Render report and return it's id
     * @param request Report render request
     * @return Report url
     */
    public String render(RenderRequest request) throws Exception {
        String reportId = UUID.randomUUID().toString();
        logger.info("Started report with id {}", reportId);

        if(request.getDesign() == null || request.getDesign().length() == 0) {
            throw new Exception("Report design is null");
        }

        IReportEngine reportEngine = engine.getReportEngine();
        InputStream reportText = new ByteArrayInputStream(request.getDesign().getBytes());

        try {
            RenderOption renderOption = getFormatOption(reportId, request.getFormat());
            IReportRunnable design = reportEngine.openReportDesign(reportText);
            IRunAndRenderTask task = reportEngine.createRunAndRenderTask(design);
            task.setRenderOption(renderOption);

            if(request.getParams() != null) {
                task.setParameterValues(request.getParams());
            }

            task.run();
            task.close();
            logger.info("Finished report with id {}", reportId);
        } catch(Exception e) {
            logger.error("Report rendering error", e);
            throw new Exception("Report rendering error");
        }

        reportText.close();

        return getReportUrl(reportId, request.getFormat());
    }

    /**
     * Report format description
     * @param reportId Report id
     * @param format String format name
     * @return BIRT engine format description
     */
    private RenderOption getFormatOption(String reportId, String format) {
        String outputPath = Paths.get("public", reportId).toString();

        RenderOption option;

        switch(format.toLowerCase()) {
            case "pdf":
                PDFRenderOption pdfOption = new PDFRenderOption();
                pdfOption.setOutputFormat("pdf");
                option = pdfOption;
                break;

            case "doc":
                option = new RenderOption();
                option.setOutputFormat("doc");
                break;

            case "docx":
                DocxRenderOption docxRenderOption = new DocxRenderOption();
                docxRenderOption.setOutputFormat("docx");
                option = docxRenderOption;
                break;

            case "xls":
                option = new RenderOption();
                option.setOutputFormat("xls");
                break;

            case "ppt":
                option = new RenderOption();
                option.setOutputFormat("ppt");
                break;

            case "pptx":
                option = new RenderOption();
                option.setOutputFormat("pptx");
                break;

            case "postscript":
                option = new RenderOption();
                option.setOutputFormat("postscript");
                break;

            case "html":
            default:
                HTMLRenderOption htmlOption = new HTMLRenderOption();
                htmlOption.setOutputFormat("html");
                htmlOption.setImageDirectory("img");
                htmlOption.setImageHandler(new HTMLCompleteImageHandler());
                option = htmlOption;
        }

        String fileName = "report." + getFileExtensionByFormat(format);
        option.setOutputFileName(Paths.get(outputPath, fileName).toString());

        return option;
    }

    /**
     * Get generated report url by id and format
     * @param reportId Report id
     * @param format Report format
     * @return Report url
     */
    private String getReportUrl(String reportId, String format) {
        return reportId + "/report." + getFileExtensionByFormat(format);
    }

    /**
     * Get report file extension by report desired format
     * @param format Report format
     * @return File extension
     */
    private String getFileExtensionByFormat(String format) {
        switch(format.toLowerCase()) {
            case "pdf":
                return "pdf";
            case "doc":
                return "doc";
            case "docx":
                return "docx";
            case "xls":
                return "xls";
            case "ppt":
                return "ppt";
            case "pptx":
                return "pptx";
            case "postscript":
                return "ps";
            case "html":
            default:
                return "html";
        }
    }

    public void setEngine(BirtEngine engine) {
        this.engine = engine;
    }
}
