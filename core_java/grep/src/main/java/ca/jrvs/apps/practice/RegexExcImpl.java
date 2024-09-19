package ca.jrvs.apps.practice;

import java.util.regex.Pattern;

public class RegexExcImpl implements RegexExc {

    public static void main(String[] args) {

        RegexExcImpl regexExcImpl = new RegexExcImpl();
        System.out.println("Testing jpg on matchJpeg: " + regexExcImpl.matchJpeg("hello.jpg"));
        System.out.println("Testing jpeg on matchJpeg: " + regexExcImpl.matchJpeg("hello.jpeg"));
        System.out.println("Testing png on matchJpeg: " + regexExcImpl.matchJpeg("helloJpeg.png"));
        System.out.println("Testing ip on matchIp: " + regexExcImpl.matchIp("192.168.2.0"));
        System.out.println("Testing ip on matchIp: " + regexExcImpl.matchIp("192.168.2.0.1.1"));
        System.out.println("Testing empty line on isEmptyLine: " + regexExcImpl.isEmptyLine("This is a line\nthis is another line"));
        System.out.println("Testing empty line on isEmptyLine: " + regexExcImpl.isEmptyLine("      "));
    }

    @Override
    public boolean matchJpeg(String filename) {
        Pattern pattern = Pattern.compile("\\w+\\.(jpg|jpeg)$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(filename).matches();
    }

    @Override
    public boolean matchIp(String ip) {
        return Pattern.matches("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$", ip);
    }

    @Override
    public boolean isEmptyLine(String line) {
        return Pattern.matches("^\\s*$", line);
    }
}
