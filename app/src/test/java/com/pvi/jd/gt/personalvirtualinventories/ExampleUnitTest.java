package com.pvi.jd.gt.personalvirtualinventories;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.*;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkMD5() {
        try {
            byte[] bytesOfMessage = "password".getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger bigInt = new BigInteger(1,thedigest);
            String hashtext = bigInt.toString(16);
            assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", hashtext);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseIngredientLine() {
        String exampleLine = "4.5 teaspoons dry yeast";
        String exampleLine2 = "5 garlic cloves minced";

        String regex = "(\\d+(\\.\\d+)?(/\\d+)?)\\s((cup(s)?)|(oz(\\.)?)|(ounce(s)?)|(tbs(p)?(\\.)?)|(tsp(\\.)?)|(tablespoon(s)?)|(teaspoon(s)?))?(\\w*)?\\s";
        String regex2 = "(\\d+(\\.\\d+)?(/\\d+)?)\\s";
        String exampleline = "13/2 cup";
        Pattern p = Pattern.compile(regex2);
        String[] results = p.split(exampleline);
        for(String s: results) {
            System.out.println(s);
        }

//        String negatedRegex = "(?!" + regex + "$).*";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(exampleLine);
//        String[] results = p.split(exampleLine);
//        for(String s: results) {
//            System.out.println(s);
//        }
//        if(m.find()) {
//            System.out.println("start(): " + m.start());
//            System.out.println("end(): " + m.end());
//            System.out.println(exampleLine.substring(m.start(), m.end()-1));
//            System.out.println(exampleLine.substring(m.end(), exampleLine.length()));
//        }
//
//        Pattern neg = Pattern.compile(negatedRegex);
//        Matcher negM = neg.matcher(exampleLine);
//        if(negM.find()){
//            System.out.println("start(): " + negM.start());
//            System.out.println("end(): " + negM.end());
//        }
//
//        Matcher example2 = p.matcher(exampleLine2);
//        if(example2.find()) {
//            System.out.println("start(): " + example2.start());
//            System.out.println("end(): " + example2.end());
//            System.out.println(exampleLine2.substring(example2.start(), example2.end()-1));
//            System.out.println(exampleLine2.substring(example2.end(), exampleLine2.length()));
//        }
    }
}