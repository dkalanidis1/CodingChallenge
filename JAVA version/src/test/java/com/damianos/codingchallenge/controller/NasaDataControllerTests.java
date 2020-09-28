package  com.damianos.codingchallenge.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;



@SpringBootTest
public class NasaDataControllerTests {

    @Autowired
    private NasaDataController nasaDataController = new NasaDataController();

    @Test
    public void testValidate() {
        String teststring = "uplherc.upl.com - - [01/Aug/1995:00:00:08 -0400] \"GET /images/ksclogo-medium.gif HTTP/1.0\" 304 0";
        boolean result=  nasaDataController.validate_line(teststring);
        Assert.assertEquals(true, result);
    }

}
