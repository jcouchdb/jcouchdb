package org.jcouchdb.document;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.svenson.JSON;


public class DesignDocumentTestCase
{
    protected final static Logger log = LoggerFactory.getLogger(DesignDocumentTestCase.class);

    private JSON jsonGen = new JSON();

    @Test
    public void toJson()
    {
        DesignDocument designDocument = new DesignDocument("test");

        View view = new View();
        view.setMap("function(doc) { if (doc.foo) map(null,doc) }");
        designDocument.addView("foo", view );

    }

    @Test
    public void thatRevisionIsJSONed()
    {
        DesignDocument designDocument = new DesignDocument("test");
        designDocument.setRevision("12345");
        View view = new View();
        view.setMap("function(doc) { if (doc.foo) map(null,doc) }");
        designDocument.addView("foo", view );

        String json = jsonGen.forValue(designDocument);

        log.debug(json);
    }

}
