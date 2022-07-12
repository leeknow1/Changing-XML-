import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        //configLimits(25,5);

        configMount("work", "workstream", 15);
    }

    public static void configMount(String typeMount, String nameMount, int listenerAmount){
        try{
            String file = "icecast.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            Element root =doc.getDocumentElement();
            Element mount=doc.createElement("mount");
            Attr type = doc.createAttribute("type");

            mount.setAttributeNode(type);
            mount.setAttribute(type.getName(), typeMount);

            Element publicMount = doc.createElement("public");
            publicMount.setTextContent(String.valueOf(1));

            Element mountName = doc.createElement("mount-name");
            mountName.setTextContent("/" + nameMount + ".ogg");

            Element mountMaxListeners = doc.createElement("max-listeners");
            mountMaxListeners.setTextContent(String.valueOf(listenerAmount));

            Element streamURL = doc.createElement("stream-url");
            streamURL.setTextContent("localhost");

            mount.appendChild(publicMount);
            mount.appendChild(mountName);
            mount.appendChild(mountMaxListeners);
            mount.appendChild(streamURL);
            root.appendChild(mount);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource src = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(file));
            transformer.transform(src, res);
        }catch (Exception e){
            e.printStackTrace();
        }

        moveFile();
    }

    public static void moveFile(){
        try{
            FileInputStream fis = new FileInputStream("icecast.xml");
            FileOutputStream fos = new FileOutputStream("C:/Program Files (x86)/Icecast/icecast.xml");

            int c;
            while ((c = fis.read()) != -1) {
                fos.write(c);
            }

            System.out.println("Successfully moved");
        }catch (IOException e){
            e.printStackTrace();
        }
    }



//    public static void configLimits(int clients, int sources){
//        try{
//            String file = "icecast.xml";
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(file);
//
//            Node clientsNode = doc.getElementsByTagName("clients").item(0);
//            Node newClients = clientsNode.getFirstChild();
//            System.out.println("Clients before: " + newClients.getTextContent());
//            newClients.setTextContent(String.valueOf(clients));
//
//
//            Node sourcesNode = doc.getElementsByTagName("sources").item(0);
//            Node newSources = sourcesNode.getFirstChild();
//            System.out.println("Sources before: " + newSources.getTextContent());
//            newSources.setTextContent(String.valueOf(sources));
//
//            System.out.println("\n\nClients after: " + newClients.getTextContent());
//            System.out.println("Sources after: " + newSources.getTextContent());
//
//            TransformerFactory tFactory = TransformerFactory.newInstance();
//            Transformer transformer = tFactory.newTransformer();
//            DOMSource src = new DOMSource(doc);
//            StreamResult res = new StreamResult(new File(file));
//            transformer.transform(src, res);
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
