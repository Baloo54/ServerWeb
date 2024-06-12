package webserver;

public class Main {
    public static void main(String[] args) {
        try{
            //create a ReaderFileConf object
            ReaderFileConf reader = new ReaderFileConf("/etc/myweb/myweb.conf");
            //read the parameter "port" in the configuration file
            System.out.println(reader.readConf("accept"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}