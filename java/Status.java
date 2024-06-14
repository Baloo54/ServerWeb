import java.io.IOException;
/**
 * Classe permettant de d'afficher le status du serveur
 *@author Thomas Fuchs
* @version 1.0
 */
public class Status 
{
    private String memory;
    private String disk;
    private String cpu;
    /**
     * Constructor of the class StatusHandler with 3 parameters memory, disk and cpu
     * @param memory
     * @param disk
     * @param cpu
     */
    public Status(String memory, String disk, String cpu)
    {
        if(memory == null || disk == null || cpu == null){
            throw new IllegalArgumentException("Memory, disk and cpu must not be null");
        }else{
            this.memory = memory;
            this.disk = disk;
            this.cpu = cpu;
        }
    }
    /**
     * méthode getStatus qui retourne le status de la memoire, du disque et du cpu
     * @return
     */
    public String getStatus() throws IOException
    {
        // StringBuilder pour stocker le status
        StringBuilder status = new StringBuilder();
        // HTML code pour afficher le status de la memoire, du disque et du cpu
        status.append("HTTP/1.1 200 OK\r\n");
        status.append("Content-Type: text/html\r\n");
        status.append("Content-Encoding: gzip\r\n\r\n");
        status.append("<html><body>");
        status.append("<h1>Status</h1>");
        status.append("<p>Memoire libre: ").append(memory).append(" bytes</p>");
        status.append("<p>Espace disque restant: ").append(disk).append(" bytes</p>");
        status.append("<p>Nombre de processeurs: ").append(cpu).append("</p>");
        status.append("</body></html>");
        return status.toString();
    }   
}   