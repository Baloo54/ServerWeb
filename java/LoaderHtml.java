import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * classe loaderHtml
 * permet de charger un fichier html
 * et de convertir les images, videos et audios en base 64
 * @author Gabriel Comte
 * @version 1.0
 */
public class LoaderHtml {
    /**
     * attribut url
     */
    private String url;
    /**
     * constructeur
     * @param url
     */
    public LoaderHtml(String url) {
        this.url = url;
    }
    /**
     * methode load
     * permet de charger un fichier html
     * @return
     */
    public String load(Log logAccess, Log logError) throws IOException{
        try{
            //creation d'un parser pour lire le fichier 
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //creation d'un document builder
            DocumentBuilder builder = factory.newDocumentBuilder();
            //creation d'un document
            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();
            // on converti les images en base 64
            String[] convertion = {"img", "video", "audio"};
            // on definit les formats
            String[] format = {"image/", "video/", "audio/"};
            // vérification de présence des balises
            boolean type = false;
            int i = 0;
            for (String tag : convertion){
                NodeList nList = doc.getElementsByTagName(tag);
                //parcours de la liste des noeuds
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    type = true;
                    //recuperation du noeud
                    Node nNode = nList.item(temp);
                    //recuperation de l'attribut src
                    String src = nNode.getAttributes().getNamedItem("src").getNodeValue();
                    //recuperation du fichier
                    FileInputStream fis = new FileInputStream(src);
                    byte[] imageInByte = new byte[(int) src.length()];
                    fis.read(imageInByte);
                    fis.close();
                    String base64String = Base64.getEncoder().encodeToString(imageInByte);
                    // ajout de l'image à la reponse 
                    //on definit le format de l'image en fonction de l'extension
                    nNode.getAttributes().getNamedItem("src").setNodeValue("data:"+format[i]+src.split("\\.")[1]+";base64," + base64String);
                }
                i++;
            }
            String reponse = "HTTP/1.1 200 OK\r\n";
            //on definit l'encodage de la reponse
            reponse += "Content-Encoding: gzip\r\n";
            //on definit le type de la reponse
            reponse += type ? "Content-Type: multipart/form-data;\r\n\r\n" : "Content-Type: text/html\r\n\r\n";
            //on converti le document en byte
            logAccess.writeLog("File " + url + " loaded");
            return reponse + doc.toString();   
        }catch(Exception e){
            logError.writeLog("File " + url + " not found");
            return "HTTP/1.1 404 Not Found\n\n";
            
        }
    }
}
