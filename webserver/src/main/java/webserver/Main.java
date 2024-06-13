package webserver;

public class Main {
    public static void main(String[] args) {
        try{
            ReaderFile reader = new ReaderFile("/etc/myweb/myweb.conf");
            System.out.println(reader.readConf("webconf"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}