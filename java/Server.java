import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.ArrayList;

/**
 * classe Server 
 * permet de lancer un serveur
 * @author Gabriel Comte
 * @version 1.0
 */
public class Server {
    /**
     * attributs permettant de gerer le serveur
     */
    private ReaderFileConf reader;
    private FilterIP filter;
    private Log logAccess;
    private Log logError;
    private Status status;
    private LoaderHtml loader;
    /**
     * constructeur
     * @param path
     */
    public Server() throws Exception{ 
        reader = new ReaderFileConf("/etc/myweb/myweb.conf"); 
    }
    /**
     * méthode filter
     * @param ip adresse ip du client
     * @return boolean True si l'adresse ip est autorisée, False sinon
     */
    public boolean filter(String ip) throws Exception{
        ArrayList<String> list = reader.readConf("accept");
        ArrayList<String> list2 = reader.readConf("reject");
        filter = new FilterIP(ip);
        boolean accept = false;
        for (String s : list){
            accept = filter.passIP(s) ? true : accept;
        }
        for (String s : list2){
            accept = filter.passIP(s) ? false : accept;
        }
        return accept;
    }
    /**
     * méthode main
     * lancement du serveur
     */
    public static void main(String[] args) throws Exception{
        Server server = new Server();
        //creation des logs
        server.logAccess = new Log(server.reader.readConf("accesslog").get(0));
        server.logError = new Log(server.reader.readConf("errorlog").get(0));
        //creation de l'interpreteur
        Interpret interpret = new Interpret("C:\\msys64\\ucrt64\\bin\\python3.exe");
        try{
            //creation d'un serveur avec le port du fichier de configuration
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(server.reader.readConf("port").get(0)));
            while(true){
                Socket socket = serverSocket.accept();
                //recuperation de l'adresse ip du client 
                String ip = socket.getInetAddress().getHostAddress();
                ip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
                //filtrage de l'ip
                if(!server.filter(ip)){
                    server.logError.writeLog("IP " + ip + " refused");
                    String message = "HTTP/1.1 403 Forbidden\n\n";
                    socket.getOutputStream().write(message.getBytes());
                }else{
                    server.logAccess.writeLog("IP " + ip + " connected");
                    //envoi de la reponse
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String request;
                    do {
                        request = reader.readLine();
                    } while (request != null  != request.split(" ")[0].equals("GET"));
                    // permet de récupérer le fichier demandé
                    request = request.split(" ")[1].substring(1);
                    // si la requête est @Status alors on affiche le status du serveur
                    if(request.equals("@Status")){
                        Runtime runtime = Runtime.getRuntime(); //recuperation de la memoire
                        long freeMemory = runtime.freeMemory(); // recuperation de la memoire libre
                        int availableProcessors = runtime.availableProcessors(); // recuperation du nombre de processeurs
                        long freeDiskSpace = 0;
                        // recuperation de l'espace disque
                        for (FileStore store : FileSystems.getDefault().getFileStores()) {
                            freeDiskSpace += store.getUsableSpace();
                        }
                        //log
                        server.logAccess.writeLog("Status of the server");
                        //affichage du status
                        server.status = new Status(Long.toString(freeMemory), Long.toString(freeDiskSpace), Integer.toString(availableProcessors));
                        //envoi de la reponse
                        socket.getOutputStream().write(server.status.getStatus().getBytes());
                    }else{
                        //chargement du fichier html
                        server.loader = new LoaderHtml(server.reader.readConf("root").get(0) + request);
                        //le log se fait dans la methode load
                        //envoi de la reponse
                        socket.getOutputStream().write(server.loader.load(server.logAccess, server.logError).getBytes());
                        //interpretation du script python
                        socket.getOutputStream().write(interpret.interpreteurPyhton().getBytes());

                    }
                }     
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
