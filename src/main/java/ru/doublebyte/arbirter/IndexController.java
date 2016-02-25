package ru.doublebyte.arbirter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.doublebyte.arbirter.types.RenderRequest;
import ru.doublebyte.arbirter.types.RenderResponse;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ReportRenderer reportRenderer;

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public RenderResponse index(@RequestBody RenderRequest request) {
        try {
            String reportUrl = reportRenderer.render(request);
            return new RenderResponse(true, "OK", reportUrl);
        } catch(Exception e) {
            return new RenderResponse(false, e.getMessage(), "");
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String help() {
        return "Only POST allowed.";
    }

    public void setReportRenderer(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }
}
