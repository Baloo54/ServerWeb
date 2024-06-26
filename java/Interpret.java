import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe permettant d'interpreter un script python
 * @author Thomas Fuchs
 * @version 1.0
 */
public class Interpret
{   

    private final String python;

    /**
     * Constructeur de la classe Interpret
     * @param path
     * @param python
     */
    public Interpret(String python)
    {
        this.python = python;
    }
    
    /*
     * Methode permettant d'interpreter un script python
     * @throws IOException
     */
    public String interpreteurPyhton() throws IOException
    {
        /*
        try {
        ProcessBuilder pb = new ProcessBuilder(python, scriptPath);
        Process p = pb.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String ret;
        while ((ret = in.readLine()) != null) {
            System.out.println(ret);
        }
        }catch (IOException e)
        {   
            System.err.println("Erreur lors de l'execution du script python");
            e.getMessage();
        }
        */

        //Version sans fichier python
        
         String pythonCode = "import time; print(time.time())";

        /**
         * Execution du script python
         */


        String res = "";
        try {
            ProcessBuilder pb = new ProcessBuilder(python, "-c", pythonCode);
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret;
            
            while ((ret = in.readLine()) != null) 
            {
                res+=ret;
            }
            } catch (IOException e)
            {   
                System.err.println("Erreur lors de l'execution du script python");
                e.getMessage();
            }
        return res;
    }
}