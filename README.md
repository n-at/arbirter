arbirter - Alternative renderer of BIRT engine reports
======================================================

Building
--------

Java 8 and maven 3 are required. Build with command:

    mvn clean package

Running
-------

Run from `target`:

    java -jar arbirter.jar

Bu default arbirter uses port `8080`.

Send POST request to / with JSON:

    {
        "format": "html",
        "design": "...",
        "params": {...}
    }

* `format` can be one of: `html, pdf, doc, docx, xls, ppt, pptx, postscript`.
* `design` is BIRT .rptdesign file contents.
* `params` may contain report parameters.

Response will contain url of generated report:

    {
        "success": true,
        "message": "OK",
        "url": "f4156008-a32f-44ab-b52a-9cb908ab048a/report.html"
    }

License
-------

BSD
