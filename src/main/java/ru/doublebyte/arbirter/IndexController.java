package ru.doublebyte.arbirter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import ru.doublebyte.arbirter.types.RenderRequest;
import ru.doublebyte.arbirter.types.RenderResponse;

import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ReportRenderer reportRenderer;

    @RequestMapping(path = "/render", method = RequestMethod.POST)
    public RenderResponse index(@RequestBody RenderRequest request) {
        try {
            String reportUrl = reportRenderer.render(request);
            return new RenderResponse(true, "OK", reportUrl);
        } catch(Exception e) {
            return new RenderResponse(false, e.getMessage(), "");
        }
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public RenderResponse syntaxErrorHandler(HttpMessageNotReadableException e) {
        logger.error("Request syntax error", e);
        return new RenderResponse(false, "Request syntax error: " + e.getMessage(), "");
    }

    @ExceptionHandler({Exception.class})
    public RenderResponse defaultErrorHandler(Exception e) {
        logger.error("Application error", e);
        return new RenderResponse(false, "Application error: " + e.getMessage(), "");
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }
}
