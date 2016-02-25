package ru.doublebyte.arbirter.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RenderRequest {

    private String format;
    private String design;
    private Map<String, Object> params;

    ///////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return String.format("RenderRequest[format=%s]", format);
    }

    ///////////////////////////////////////////////////////////////////////////

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
