import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PR140Main {

    public static void Main(String[] args) {
        try{
            File file = new File("src/data/persones.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList listPersones = doc.getElementsByTagName("persona");
            String linea = new String(new char[53]).replace('\0', '-');
            System.out.printf("%-15s %-15s %-8s %-15s\n%s\n", "Nom", "Cognom", "Edat", "Ciutat", linea);

            for(int i = 0; i<listPersones.getLength(); i++){
                Node pers = listPersones.item(i);

                if(pers.getNodeType() == Node.ELEMENT_NODE){
                    Element elm = (Element) pers;

                    NodeList nodeList = elm.getElementsByTagName("nom");
                    String nom = nodeList.item(0).getTextContent();
                    nodeList = elm.getElementsByTagName("cognom");
                    String cognom = nodeList.item(0).getTextContent();
                    nodeList = elm.getElementsByTagName("edat");
                    String edat = nodeList.item(0).getTextContent();
                    nodeList = elm.getElementsByTagName("ciutat");
                    String ciutat = nodeList.item(0).getTextContent();
                    System.out.printf("%-15s %-15s %-8s %-15s\n", nom, cognom, edat, ciutat);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
