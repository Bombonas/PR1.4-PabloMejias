import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PR142Main {
    public static void Main(String[] args) {
        listItems();
        mostrarModuls("AMS2");
        listAlumnes("AMS2");
    }

    public static void listItems(){
        File file = new File("src/data/cursos.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "/cursos/curs";
            NodeList IdCursos = (NodeList) xPath.evaluate(expression, doc, XPathConstants.NODESET);

            System.out.println("\n=======================\nPart 1.\nCURSOS IDs:");
            for (int i = 0; i < IdCursos.getLength(); i++) {
                Node element = IdCursos.item(i);
                String id = ((Element) element).getAttribute("id");
                System.out.println(id);
            }

            System.out.println("\nTUTORS:");
            expression = "/cursos/curs/tutor";
            NodeList tutors = (NodeList) xPath.evaluate(expression, doc, XPathConstants.NODESET);
             for (int i = 0; i < tutors.getLength(); i++) {
                Node element = tutors.item(i);
                String name = ((Element) element).getTextContent();
                System.out.println(name);
            }

            System.out.printf("\nNUM ALUMNES:");
            expression = "count(/cursos/curs/alumnes/alumne)";

            double count = (Double) xPath.evaluate(expression, doc, XPathConstants.NUMBER);

            int numberOfElements = (int) count; // Convertir el resultado a un entero
            System.out.println(count);

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        } 
    }

    public static void mostrarModuls(String id){

        File file = new File("src/data/cursos.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("\n=======================\nPart 2.\nID curs: " + id);

            String xpathExpression = "/cursos/curs[@id='" + id + "']/moduls/modul";

            NodeList modules = (NodeList) xPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
            for (int i = 0; i < modules.getLength(); i++) {
                Element module = (Element) modules.item(i);
                String moduleId = module.getAttribute("id");
                String moduleTitle = module.getElementsByTagName("titol").item(0).getTextContent();
                System.out.println("ID modul: " + moduleId);
                System.out.println("Titol modul: " + moduleTitle);
            }
        }catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        } 
    }

    public static void listAlumnes(String id){
        
    }
}
