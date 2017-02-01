package ru.doublebyte.arbirter.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.doublebyte.arbirter.types.RenderRequest;
import ru.doublebyte.arbirter.types.RenderResponse;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexControllerTest {

    @Autowired
    private IndexController indexController;

    @Test
    public void testIndex() throws Exception {
        testNormalDesign();
        testEmptyDesign();
        testFormats();
    }

    /**
     * Report with normal design
     */
    private void testNormalDesign() {
        Map<String, Object> params = new HashMap<>();
        params.put("sample", "test");

        RenderRequest request = new RenderRequest();
        request.setFormat("html");
        request.setParams(params);
        request.setDesign(design);

        RenderResponse response = indexController.index(request);
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("OK", response.getMessage());
        assertTrue(reportFileExists(response.getUrl()));
    }

    /**
     * Report with empty design (incorrect)
     */
    private void testEmptyDesign() {
        RenderRequest request = new RenderRequest();
        request.setFormat("html");
        request.setDesign("");

        RenderResponse response = indexController.index(request);
        assertFalse(response.isSuccess());
        assertEquals("", response.getUrl());
    }

    /**
     * Test all output formats
     */
    private void testFormats() {
        List<String> formats = Arrays.asList("html", "pdf", "doc", "docx",
                "xls", "ppt", "pptx", "odt", "ods", "odp", "postscript");

        Map<String, Object> params = new HashMap<>();
        params.put("sample", "test");

        RenderRequest request = new RenderRequest();
        request.setDesign(design);
        request.setParams(params);

        for(String format : formats) {
            request.setFormat(format);

            RenderResponse response = indexController.index(request);
            assertTrue(response.isSuccess());
            assertEquals("OK", response.getMessage());
            assertTrue(reportFileExists(response.getUrl()));
        }
    }

    /**
     * Check whether report file exists
     * @param reportUrl Url of report file
     * @return Check result
     */
    private boolean reportFileExists(String reportUrl) {
        String[] path = reportUrl.split("/");
        String reportId = path[0], fileName = path[1];
        Path reportPath = Paths.get("public", reportId, fileName);
        return Files.exists(reportPath);
    }

    private String design = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<report xmlns=\"http://www.eclipse." +
            "org/birt/2005/design\" version=\"3.2.6\" id=\"1\">\n<property name=\"author\">Bertie the " +
            "Platypus</property>\n<property name=\"createdBy\">Eclipse BIRT Designer Version 1.0.0 Bui" +
            "ld &lt;20050405-1230></property>\n<property name=\"units\">in</property>\n<property name=" +
            "\"comments\">Not a very interesting report, just a \"Hello World\" with a param.</propert" +
            "y>\n<html-property name=\"description\">Sample report used to test the BIRT viewer.</html" +
            "-property>\n<list-property name=\"configVars\">\n<structure>\n<property name=\"name\">sam" +
            "ple</property>\n<property name=\"value\">aaa</property>\n</structure>\n</list-property>\n" +
            "<parameters>\n<scalar-parameter name=\"sample\" id=\"2\">\n<text-property name=\"displayN" +
            "ame\">Sample Parameter</text-property>\n<property name=\"hidden\">false</property>\n<prop" +
            "erty name=\"dataType\">string</property>\n<property name=\"concealValue\">false</property" +
            ">\n<property name=\"allowBlank\">true</property>\n<property name=\"allowNull\">false</pro" +
            "perty>\n<property name=\"controlType\">text-box</property>\n<property name=\"mustMatch\">" +
            "false</property>\n<property name=\"fixedOrder\">false</property>\n</scalar-parameter>\n</" +
            "parameters>\n<page-setup>\n<simple-master-page name=\"Simple MasterPage\" id=\"3\">\n<pag" +
            "e-header>\n<grid id=\"4\">\n<property name=\"width\">100%</property>\n<column id=\"5\"/>" +
            "\n<row id=\"6\">\n<cell id=\"7\">\n<property name=\"fontSize\">xx-large</property>\n<prop" +
            "erty name=\"fontWeight\">bold</property>\n<property name=\"textAlign\">center</property>" +
            "\n<text id=\"8\">\n<text-property name=\"content\"><![CDATA[Title]]></text-property>\n</t" +
            "ext>\n</cell>\n</row>\n</grid>\n</page-header>\n<page-footer>\n<grid id=\"9\">\n<property" +
            " name=\"width\">100%</property>\n<column id=\"10\"/>\n<column id=\"11\"/>\n<row id=\"12\"" +
            ">\n<cell id=\"13\">\n<text id=\"14\">\n<property name=\"contentType\">html</property>\n<t" +
            "ext-property name=\"content\"><![CDATA[<value-of>new Date()</value-of>]]></text-property>" +
            "\n</text>\n</cell>\n<cell id=\"15\">\n<property name=\"textAlign\">right</property>\n<aut" +
            "o-text id=\"26\">\n<property name=\"type\">page-number</property>\n</auto-text>\n</cell>" +
            "\n</row>\n</grid>\n</page-footer>\n</simple-master-page>\n</page-setup>\n<body>\n<text id" +
            "=\"17\">\n<property name=\"contentType\">html</property>\n<text-property name=\"content\"" +
            "><![CDATA[<b>Congratulations!</b>\n<br><br>\nIf you can see this report, it means that th" +
            "e BIRT viewer is installed correctly.\n<br><br>]]></text-property>\n</text>\n<grid id=\"1" +
            "8\">\n<property name=\"width\">100%</property>\n<column id=\"19\">\n<property name=\"widt" +
            "h\">1.354in</property>\n</column>\n<column id=\"20\">\n<property name=\"width\">5.083in</" +
            "property>\n</column>\n<row id=\"21\">\n<cell id=\"22\">\n<label id=\"23\">\n<text-propert" +
            "y name=\"text\">Sample Parameter:</text-property>\n</label>\n</cell>\n<cell id=\"24\">\n<" +
            "data id=\"25\">\n<list-property name=\"boundDataColumns\">\n<structure>\n<property name=" +
            "\"name\">params[\"sample\"]</property>\n<expression name=\"expression\">params[\"sample\"" +
            "]</expression>\n</structure>\n</list-property>\n<property name=\"resultSetColumn\">params" +
            "[\"sample\"]</property>\n</data>\n</cell>\n</row>\n</grid>\n</body>\n</report>\n";
}