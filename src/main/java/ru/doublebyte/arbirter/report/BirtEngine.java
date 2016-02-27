package ru.doublebyte.arbirter.report;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Level;

@Component
public class BirtEngine {

    private static final Logger logger = LoggerFactory.getLogger(BirtEngine.class);

    private IReportEngine reportEngine = null;

    @PostConstruct
    public void init() {
        EngineConfig engineConfig = new EngineConfig();
        engineConfig.setLogConfig("logs", Level.WARNING);

        try {
            Platform.startup(engineConfig);
        } catch(Exception e) {
            logger.error("Birt platform startup error", e);
            System.exit(2);
        }

        IReportEngineFactory factory =
                (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        reportEngine = factory.createReportEngine(engineConfig);
    }

    @PreDestroy
    public void destroy() {
        reportEngine.destroy();
        Platform.shutdown();
    }

    public IReportEngine getReportEngine() {
        return reportEngine;
    }
}
