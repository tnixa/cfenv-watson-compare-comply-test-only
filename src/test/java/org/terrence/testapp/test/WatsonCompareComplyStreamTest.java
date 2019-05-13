package org.terrence.testapp.test;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;
import org.terrence.testapp.rest.TestRestController;

public class WatsonCompareComplyStreamTest {

    // verify file is not null
    @Test
    public void inputStreamTest() throws Exception {
        InputStream stream = TestRestController.createInputStream();
        assertNotNull(stream);
    }
}