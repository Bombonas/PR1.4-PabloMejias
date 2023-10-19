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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PR142Main {
    public static void Main(String[] args) {
        listItems();
    }

    public static void listItems(){
        File file = new File("src/data/cursos.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();

            String expression = "/cursos/curs/@id";
            NodeList listExpression = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            expression = "count(/cursos/curs)";
            Double count = (Double) xPath.evaluate(expression, doc, XPathConstants.NUMBER);
            int numberOfElements = count.intValue();
            System.out.println(numberOfElements);

            Element elm = (Element) listExpression.item(0);

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        } 
    }
}
