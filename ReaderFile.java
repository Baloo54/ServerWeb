import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe permettant de lire un fichier de configuration
 * @author Gabriel Comte
 * @version 1.0
 */
public class ReaderFile {
    /**
     * attribut permettant de stocker le chemin du fichier de configuration
     */
    private final File file;
    /**
     * Constructeur de la classe
     * @param path lien du fichier de configuration
     */
    public ReaderFile(String path) throws Exception{
        file = new File(path);
    }
    /**
     * methode pour lire un parametre dans le fichier de configuration
     * @param param parametre a lire
     * @return une ArrayList
     */
    public ArrayList<String> readConf(String param) throws Exception{
        //creation d'une arraylist pour stocker les valeurs
        ArrayList<String> list = new ArrayList<String>();
        try{
            //creation d'un parser pour lire le fichier 
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //creation d'un document builder
            DocumentBuilder builder = factory.newDocumentBuilder();
            //creation d'un document
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(param);
            //parcours de la liste des noeuds
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //ajout de la valeur dans l'arraylist
                    list.add(eElement.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
