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

        //TODO check request

        IReportEngine reportEngine = engine.getReportEngine();
        InputStream reportText = new ByteArrayInputStream(request.getDesign().getBytes());

        try {
            RenderOption renderOption = getFormatOption(reportId, request.getFormat());
            IReportRunnable design = reportEngine.openReportDesign(reportText);
            IRunAndRenderTask task = reportEngine.createRunAndRenderTask(design);
            task.setParameterValues(request.getParams());
            task.setRenderOption(renderOption);
            task.run();
            task.close();
            logger.info("Finished report with id {}", reportId);
        } catch(Exception e) {
            logger.error("Report rendering error", e);
            throw new Exception("Report rendering error");
        }

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

        String reportExtension;
        RenderOption option;

        switch(format.toLowerCase()) {
            case "pdf":
                PDFRenderOption pdfOption = new PDFRenderOption();
                pdfOption.setOutputFormat("pdf");
                reportExtension = "pdf";
                option = pdfOption;
                break;

            case "doc":
                option = new RenderOption();
                option.setOutputFormat("doc");
                reportExtension = "doc";
                break;

            case "docx":
                DocxRenderOption docxRenderOption = new DocxRenderOption();
                docxRenderOption.setOutputFormat("docx");
                reportExtension = "docx";
                option = docxRenderOption;
                break;

            case "html":
            default:
                HTMLRenderOption htmlOption = new HTMLRenderOption();
                htmlOption.setOutputFormat("html");
                htmlOption.setBaseImageURL(reportId);
                htmlOption.setImageDirectory(outputPath);
                reportExtension = "html";
                option = htmlOption;
        }

        option.setOutputFileName(Paths.get(outputPath, "report." + reportExtension).toString());

        return option;
    }

    /**
     * Get generated report url by id and format
     * @param reportId Report id
     * @param format Report format
     * @return Report url
     */
    private String getReportUrl(String reportId, String format) {
        switch(format.toLowerCase()) {
            case "pdf":
                return reportId + "/report.pdf";
            case "doc":
                return reportId + "/report.doc";
            case "docx":
                return reportId + "/report.docx";
            case "html":
            default:
                return reportId + "/report.html";
        }
    }

    public void setEngine(BirtEngine engine) {
        this.engine = engine;
    }
}
