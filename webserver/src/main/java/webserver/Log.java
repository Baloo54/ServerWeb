package webserver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * @author Thomas Fuchs 
 * @version 1.0
 */
public class Log
{
    /**
     * Chemin vers le dossier contenant les logs
     * @param pathToFolder
     */
    private final String pathToFolder;
    /**
     * Constructeur de la classe Log
     * @param path
     * @throws IOException
     */
    public Log(String path) throws IOException
    {
        this.pathToFolder=path;
    }
    /**
     * Ecriture dans le fichier de log
     * @param status
     * @param message
     * @throws IOException
     */
    public void writeLog(boolean status, String message) throws IOException
    {
        try
        {   
            //Date et heure actuelle
            LocalDateTime dateObj = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String dateFormatee = dateObj.format(format);

            PrintWriter out;
            //Ecriture dans le fichier de log acceslog.log ou errorlog.log selon le status
            out = new PrintWriter(new FileWriter(pathToFolder+ (status ? "acceslog.log" : "errorlog.log"),true));
            out.println(dateFormatee+message);
            out.close();

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}