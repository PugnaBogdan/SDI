package Repository;

import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RentalXMLRepository extends InMemoryRepository<Integer, RentAction> {
    private String fileName;

    public RentalXMLRepository( String fileName) throws IOException, SAXException, ParserConfigurationException {
        this.fileName = fileName;
        loadData();
    }

    private static RentAction createRentalFromElement(Element clientElement){
        RentAction rentAction = new RentAction();
        String rentId = clientElement.getAttribute("Id");
        rentAction.setRentId(Integer.parseInt(rentId));
        Node clientNode = clientElement.getElementsByTagName("ClientId").item(0);
        String clientId = clientNode.getTextContent();
        rentAction.setClientId(Integer.parseInt(clientId));
        Node movieNode = clientElement.getElementsByTagName("MovieId").item(0);
        String movieId = movieNode.getTextContent();
        rentAction.setMovieId(Integer.parseInt(movieId));
        return rentAction;
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
                .map(node -> createRentalFromElement((Element) node))
                .collect(Collectors.toList())
                .forEach(rental -> {
                    try {
                        super.save(rental);
                    } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void saveToFile(RentAction rentAction) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        Node clientNode = rentToNode(rentAction, document);
        root.appendChild(clientNode);

        Transformer transformer= TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    public static Node rentToNode(RentAction rent, Document document){
        Element clientElement = document.createElement("Rent");
        clientElement.setAttribute("Id", Integer.toString(rent.getRentId()));
        Element nameElement = document.createElement("ClientId");
        nameElement.setTextContent(Integer.toString(rent.getClientId()));
        clientElement.appendChild(nameElement);
        Element ageElement = document.createElement("MovieId");
        ageElement.setTextContent(Integer.toString(rent.getMovieId()));
        clientElement.appendChild(ageElement);
        return clientElement;
    }

    @Override
    public Optional<RentAction> save(RentAction entity) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<RentAction> optional = super.save(entity);
        if (optional.isPresent()) {
            throw new ValidatorException("Client already exists!");

        }
        saveToFile(entity);
        return Optional.empty();
    }

    public void deleteRentByClient(Integer clientId) throws ValidatorException{
        Iterable<RentAction> rentals = super.findAll();
        rentals.forEach(Rent->{
            if(Rent.getClientId() == clientId){
                try {
                    this.delete(Rent.getRentId());
                } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteRentByMovie(Integer movieId) throws ValidatorException{
        Iterable<RentAction> rentals = super.findAll();
        rentals.forEach(Rent->{
            if(Rent.getMovieId() == movieId){
                try {
                    this.delete(Rent.getRentId());
                } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Optional<RentAction> delete(Integer integer) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<RentAction> optional = super.delete(integer);
        redoFile() ;
        return Optional.empty();
    }

    @Override
    public Optional<RentAction> update(RentAction entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        super.update(entity);
        redoFile();
        return Optional.empty();
    }

    private void redoFile() throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("RentRep");
        document.appendChild(root);
        super.findAll().forEach(rentAction -> {
            Node rent = rentToNode(rentAction,document);
            root.appendChild(rent);
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
