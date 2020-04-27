package cn.gshkb.io;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

@SpringBootTest
public class ClassLoaderTest {

    @Test
    public  void testLoader(){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(URL url : urls){
            System.out.println(url.toExternalForm());
        }
    }
}
