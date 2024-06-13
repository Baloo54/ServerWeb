package webserver;

public class Main {
    public static void main(String[] args) {
        FilterIP filtre = new FilterIP("192.168.0.2");
        System.out.println(filtre.passIP("192.168.0.0/31"));
    }
}