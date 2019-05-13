package org.terrence.testapp.rest;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.watson.compare_comply.v1.CompareComply;
import com.ibm.watson.compare_comply.v1.model.ClassifyElementsOptions;
import com.ibm.watson.compare_comply.v1.model.ClassifyReturn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

  @Autowired
  protected CompareComply compareComply;

  // Create the input stream from the pdf file

  public static InputStream createInputStream() throws Exception {
    InputStream inputStream = TestRestController.class.getResourceAsStream("/test.pdf");
    return inputStream;
  }

  // Test by analyzing the pdf

  @RequestMapping(value = "/test", produces = "text/plain")
  public String runTest() {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    try {
      pw.println("Beginning test...");

      ClassifyElementsOptions classifyElementsOptions = new ClassifyElementsOptions.Builder().file(createInputStream())
          .fileContentType(HttpMediaType.APPLICATION_PDF).build();

      ClassifyReturn result = compareComply.classifyElements(classifyElementsOptions).execute().getResult();

      System.out.println(result);

      // check to see if expected keyword exists in the results
      String expectedKeyword = "Adobe";
      if (result.toString().toLowerCase().contains(expectedKeyword.toLowerCase())) {
        pw.println("PASS:  Compare and Comply analysis results contain expected keyword: " + expectedKeyword);
      } else {
        pw.println("FAIL: Compare and Comply analysis results do not contain expected keyword: " + expectedKeyword);
      }

    } catch (Exception e) {
      pw.println("FAIL: Unexpected error during test.");
      e.printStackTrace();
    }
    pw.flush();
    return sw.toString();
  }
}