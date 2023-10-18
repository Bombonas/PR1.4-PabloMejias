import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class PR141Main {
    public static void Main(String[] args) {
        // Crea una factoria de constructors de documents
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // Crea un constructor de documents
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            // Crea un nou document XML
            Document doc = db.newDocument();
            // Crea l'element root del document XML
            Element elmRoot = doc.createElement("biblioteca");
            // Afegeix l'element root al document XML
            doc.appendChild(elmRoot);
            // Crea l'element "llibre"
            Element elmLlibre = doc.createElement("llibre");
            // Crea un atribut "id"
            Attr attrId = doc.createAttribute("id");
            // Estableix el valor de l'atribut "id"
            attrId.setValue("001");
            // Afegeix l'atribut "id" a l'element "to"
            elmLlibre.setAttributeNode(attrId);


            // Crea l'element "titol"
            Element elmtitol = doc.createElement("titol");
            // Crea un node de text per al nom del destinatari
            Text elmTexttitol = doc.createTextNode("El viatge dels venturons");
            // Afegeix el node de text a l'element "llibre"
            elmtitol.appendChild(elmTexttitol);
            // Afegeix l'element "titol" a l'element root
            elmLlibre.appendChild(elmtitol);

            // Crea l'element "titol"
            Element elmautor = doc.createElement("autor");
            // Crea un node de text per al nom del destinatari
            Text elmTextautor = doc.createTextNode("Joan Pla");
            // Afegeix el node de text a l'element "llibre"
            elmautor.appendChild(elmTextautor);
            // Afegeix l'element "titol" a l'element root
            elmLlibre.appendChild(elmtitol);

            // Crea l'element "anyPublicacio"
            Element elmanyPublicacio = doc.createElement("anyPublicacio");
            // Crea un node de text per al nom del destinatari
            Text elmTextanyPublicacio = doc.createTextNode("1998");
            // Afegeix el node de text a l'element "llibre"
            elmanyPublicacio.appendChild(elmTextanyPublicacio);
            // Afegeix l'element "titol" a l'element root
            elmLlibre.appendChild(elmanyPublicacio);

            // Crea l'element "anyPublicacio"
            Element elmeditorial = doc.createElement("editorial");
            // Crea un node de text per al nom del destinatari
            Text elmTexteditorial = doc.createTextNode("Edicions Mar");
            // Afegeix el node de text a l'element "llibre"
            elmeditorial.appendChild(elmTexteditorial);
            // Afegeix l'element "titol" a l'element root
            elmLlibre.appendChild(elmeditorial);

            // Crea l'element "genere"
            Element elmgenere = doc.createElement("genere");
            // Crea un node de text per al nom del destinatari
            Text elmTextgenere = doc.createTextNode("Aventura");
            // Afegeix el node de text a l'element "llibre"
            elmgenere.appendChild(elmTextgenere);
            // Afegeix l'element "titol" a l'element root
            elmLlibre.appendChild(elmgenere);

            // Crea l'element "genere"
            Element elmpagines = doc.createElement("pagines");
            // Crea un node de text per al nom del destinatari
            Text elmTextpagines = doc.createTextNode("320");
            // Afegeix el node de text a l'element "llibre"
            elmpagines.appendChild(elmTextpagines);
            // Afegeix l'element "titol" a l'element root
            elmLlibre.appendChild(elmpagines);

            // Crea l'element "disponible"
            Element elmdisponible = doc.createElement("disponible");
            // Crea un node de text per al nom del destinatari
            Text elmTextdisponible = doc.createTextNode("true");
            // Afegeix el node de text a l'element "llibre"
            elmdisponible.appendChild(elmTextdisponible);
            // Afegeix l'element "titol" a l'element root
            elmLlibre.appendChild(elmdisponible);

            elmRoot.appendChild(elmLlibre);

            write("./src/data/biblioteca.xml", doc);
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }  
    }
    static public void write (String path, Document doc) throws TransformerException, IOException {
    if (!new File(path).exists()) { new File(path).createNewFile(); }
    // Crea una factoria de transformadors XSLT
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    // Crea un transformador XSLT
    Transformer transformer = transformerFactory.newTransformer();
    // Estableix la propietat OMIT_XML_DECLARATION a "no" per no ometre la declaració XML del document XML resultant
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    // Estableix la propietat INDENT a "yes" per indentar el document XML resultant
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    // Crea una instància de DOMSource a partir del document XML
    DOMSource source = new DOMSource(doc);
    // Crea una instància de StreamResult a partir del camí del fitxer XML
    StreamResult result = new StreamResult(new File(path));
    // Transforma el document XML especificat per source i escriu el document XML
    // resultant a l'objecte especificat per result
    transformer.transform(source, result);
}

}
