package cn.gshkb.curator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
      String path =  "http%3A%2F%2Ftest.pigeon.oos-hz.ctyunapi.cn%2Fee495901610146d2821d9d889506a63d.pdf%3FSignature%3D2Iz%252BCAGR%252B2IHEYyzd3n7R8Oi0I8%253D%26AWSAccessKeyId%3D98ceffa28018db4f524b%26Expires%3D4746907036";
      System.out.println(URLDecoder.decode(path,"UTF-8"));
    }
}
