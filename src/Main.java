import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        changeLimit(50);
    }

    public static void changeLimit(int limit){
        try{
            String file = "icecast.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            Node clients = doc.getElementsByTagName("clients").item(0);

            Node attr2 = clients.getFirstChild();
            System.out.println("Before: " + attr2.getTextContent());
            attr2.setTextContent(String.valueOf(limit));
            System.out.println("After: " + attr2.getTextContent());


            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource src = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(file));
            transformer.transform(src, res);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
