arbirter - Alternative renderer of BIRT engine reports
======================================================

Building
--------

Java 8 and maven 3 are required. 
Build jar and docker image with command:

    mvn clean package

Running
-------

Run from `target`:

    java -jar arbirter.jar

By default arbirter uses port `8080`.

Send POST request to `/render` with JSON:

    {
        "format": "html",
        "design": "...",
        "params": {...}
    }

* `format` can be one of: `html, pdf, doc, docx, xls, ppt, pptx,
odt, ods, odp, postscript`.
* `design` is BIRT .rptdesign file contents.
* `params` may contain report parameters.

Response will contain url of generated report:

    {
        "success": true,
        "message": "OK",
        "url": "f4156008-a32f-44ab-b52a-9cb908ab048a/report.html"
    }

arbirter is bundled with PostgreSQL, MySQL and Firebird JDBC drivers.

Docker
------

Docker image is available at [docker hub](https://hub.docker.com/r/atnurgaliev/arbirter/).

Start a container:

    docker run -d \
        -p 8080:8080 \
        -v /opt/arbirter/public:/app/public \
        -v /opt/arbirter/logs:/app/logs \
        --name arbirter \
        atnurgaliev/arbirter

License
-------

BSD
