public class Main {
    public static void main(String[] args) {
        try{
            Log log = new Log("/var/log/myweb/");
            log.writeLog(true, " - Acces log");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}