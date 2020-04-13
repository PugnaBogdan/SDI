package Repository.XML;

import Entities.Client;
import Entities.Movie;
import Entities.Validators.ValidatorException;
import Repository.Repository;
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
import Repository.InMemoryRepository;

public class MovieXMLRepository  extends InMemoryRepository<Integer, Movie> {
    private String fileName;

    public MovieXMLRepository( String fileName) throws IOException, SAXException, ParserConfigurationException {
        this.fileName = fileName;
        loadData();
    }

    private static Movie createMovieFromElement(Element clientElement){
        Movie mov = new Movie();
        String id = clientElement.getAttribute("Id");
        mov.setMovieId(Integer.parseInt(id));
        Node nameNode = clientElement.getElementsByTagName("Title").item(0);
        String name = nameNode.getTextContent();
        mov.setTitle(name);
        Node ageNode = clientElement.getElementsByTagName("Price").item(0);
        String age = ageNode.getTextContent();
        mov.setPrice(Integer.parseInt(age));
        return mov;
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
                .map(node -> createMovieFromElement((Element) node))
                .collect(Collectors.toList())
                .forEach(movie -> {
                    try {
                        super.save(movie);
                    } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void saveToFile(Movie movie) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(this.fileName);

        Element root = document.getDocumentElement();
        Node clientNode = movieToNode(movie, document);
        root.appendChild(clientNode);

        Transformer transformer= TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document),
                new StreamResult(new File(this.fileName)));
    }

    public static Node movieToNode(Movie movie, Document document){
        Element clientElement = document.createElement("Movie");
        clientElement.setAttribute("Id", Integer.toString(movie.getId()));
        Element nameElement = document.createElement("Title");
        nameElement.setTextContent(movie.getTitle());
        clientElement.appendChild(nameElement);
        Element ageElement = document.createElement("Price");
        ageElement.setTextContent(Integer.toString(movie.getPrice()));
        clientElement.appendChild(ageElement);
        return clientElement;
    }

    @Override
    public Optional<Movie> save(Movie entity) throws ValidatorException, ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<Movie> optional = super.save(entity);
        if (optional.isPresent()) {
            throw new ValidatorException("Movie already exists!");

        }
        saveToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Movie> delete(Integer integer) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Optional<Movie> optional = super.delete(integer);
        redoFile() ;
        return Optional.empty();
    }

    @Override
    public Optional<Movie> update(Movie entity) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        super.update(entity);
        redoFile();
        return Optional.empty();
    }

    private void redoFile() throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element root = document.createElement("MovieRep");
        document.appendChild(root);
        super.findAll().forEach(Movie -> {
            Node movie = movieToNode(Movie,document);
            root.appendChild(movie);
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
