package Repository;

import Entities.Client;
import Entities.Validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Pugna
 *
 */
public class ClientXMLRepository extends InMemoryRepository<Integer, Client> {
    private String fileName;

    public ClientXMLRepository( String fileName) throws IOException, SAXException, ParserConfigurationException {
        this.fileName = fileName;
        loadData();
    }

    private static Client createClientFromElement(Element clientElement){
        Client client = new Client();
        String id = clientElement.getAttribute("Id");
        client.setClientId(Integer.parseInt(id));
        Node nameNode = clientElement.getElementsByTagName("Name").item(0);
        String name = nameNode.getTextContent();
        client.setName(name);
        Node ageNode = clientElement.getElementsByTagName("Age").item(0);
        String age = ageNode.getTextContent();
        client.setAge(Integer.parseInt(age));
        return client;
    }

    private void loadData() throws ParserConfigurationException, IOException, SAXException {
        List<Client> result = new ArrayList<>();

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();
        IntStream
                .range(0, children.getLength())
                .mapToObj(children::item)
                .filter(node -> node instanceof Element)
                .map(node -> createClientFromElement((Element) node))
                .collect(Collectors.toList())
                .forEach(client -> {
                    try {
                        super.save(client);
                    } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void saveToFile(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        Node clientNode = clientToNode(client, document);
        root.appendChild(clientNode);

        Transformer transformer= TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    public static Node clientToNode(Client client, Document document){
        Element clientElement = document.createElement("Client");
        clientElement.setAttribute("Id", Integer.toString(client.getId()));
        Element nameElement = document.createElement("Name");
        nameElement.setTextContent(client.getName());
        clientElement.appendChild(nameElement);
        Element ageElement = document.createElement("Age");
        ageElement.setTextContent(Integer.toString(client.getAge()));
        clientElement.appendChild(ageElement);
        return clientElement;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            throw new ValidatorException("Client already exists!");

        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Client> delete(Integer integer) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<Client> optional = super.delete(integer);
        redoFile() ;
        return Optional.empty();
    }

    @Override
    public Optional<Client> update(Client entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        super.update(entity);
        redoFile();
        return Optional.empty();
    }

    private void redoFile() throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("ClientRep");
        document.appendChild(root);
        super.findAll().forEach(Client -> {
            Node child = clientToNode(Client,document);
            root.appendChild(child);
        });

        Transformer transformer = TransformerFactory.
                newInstance().
                newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileName));
        transformer.transform(domSource, streamResult);



    }


}
