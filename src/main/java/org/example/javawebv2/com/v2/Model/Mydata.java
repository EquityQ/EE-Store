package org.example.javawebv2.com.v2.Model;

public class Mydata {
    private String username;
    private String password;
    private String captcha;
    public Mydata(String username, String password, String captcha) {
        this.username = username;
        this.password = password;
        this.captcha = captcha;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getCaptcha() {
        return captcha;
    }
    @Override
    public String toString() {
        return "Mydata{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
