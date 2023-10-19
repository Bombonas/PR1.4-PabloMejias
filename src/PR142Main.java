import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
        addAlumne("Pablo, Mejias", "AMS2");
        deleteAlumne("ALVAREZ, Tomas");
        
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

            System.out.println("\n=======================\nPart 1.\n" + //
                    "=======================\nCURSOS IDs:");
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

            System.out.println("\n=======================\nPart 2.\n" + //
                    "=======================\nID curs: " + id);

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
        File file = new File("src/data/cursos.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("\n=======================\nPart 3.\n" + //
                    "=======================\nID curs: " + id);

            String xpathExpression = "/cursos/curs[@id='" + id + "']/alumnes/alumne";

            // Evaluar la expresión XPath y obtener una lista de nodos de alumnos
            NodeList students = (NodeList) xPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);

            // Iterar sobre los nodos de alumnos y listar sus nombres
            for (int i = 0; i < students.getLength(); i++) {
                Element student = (Element) students.item(i);
                String studentName = student.getTextContent();
                System.out.println(studentName);
            }
        }catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
            e.printStackTrace();
        } 
    }

    public static void deleteAlumne(String name){
        File file = new File("src/data/cursos.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("\n=======================\nPart 5.\n" + //
                    "=======================\nNom Alumne " + name);
            String xpathExpression = "/cursos/curs/alumnes/alumne[text()='" + name + "']";

            Node studentNode = (Node) xPath.evaluate(xpathExpression, doc, XPathConstants.NODE);

            if (studentNode != null) {
                Node parent = studentNode.getParentNode();
                parent.removeChild(studentNode);

                // Crear un flujo de salida para guardar el XML modificado
                OutputStream outputStream = new FileOutputStream("src/data/cursos.xml");
                StreamResult result = new StreamResult(outputStream);

                // Transformar y guardar el documento
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                transformer.transform(new DOMSource(doc), result);

                outputStream.close();
                System.out.println("=======================");
            }
        }catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException  | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void addAlumne(String name, String idCurs){
        File file = new File("src/data/cursos.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            System.out.println("\n=======================\nPart 4.\n" + //
                    "=======================\nNom Alumne " + name);

            Element studentElement = doc.createElement("alumne");
            studentElement.appendChild(doc.createTextNode(name));

            NodeList courses = doc.getElementsByTagName("curs");
            for (int i = 0; i < courses.getLength(); i++) {
                Element course = (Element) courses.item(i);
                if (course.getAttribute("id").equals(idCurs)) {
                    Element students = (Element) course.getElementsByTagName("alumnes").item(0);
                    students.appendChild(studentElement);
                    break;
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/data/cursos.xml"));
            transformer.transform(source, result);
        }catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
