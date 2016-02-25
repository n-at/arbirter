package ru.doublebyte.arbirter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.doublebyte.arbirter.types.RenderRequest;
import ru.doublebyte.arbirter.types.RenderResponse;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public RenderResponse index(@RequestBody RenderRequest request) {
        logger.info("Got request {}", request.toString());
        return new RenderResponse(false, "Not implemented yet", null);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String help() {
        return "Only POST allowed.";
    }

}
