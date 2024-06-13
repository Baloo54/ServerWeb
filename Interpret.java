import java.io.File;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Interpret
{   
    private final String path;

    public Interpret(String path)
    {
        this.path = path;
    }

    public void interpreteurPyhton(String path)
    {
        Document doc = Jsoup.parse(new File(path), "UTF-8");
        Elements codeTags = doc.select("code");
        for (Element codeTag : codeTags) 
        {
            String src = codeTag.attr("src");
            String content = codeTag.text();
            System.out.println("src: " + src);
            System.out.println("content: " + content);
        }
    
    }
   
}