package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.RentValidator;
import Entities.Validators.RentalException;
import Entities.Validators.ValidatorException;
import Repository.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class RentalController {

    private Repository<Integer, RentAction> repo;
    private RentValidator validator;
    private ClientController clientController;
    private MovieController movieController;
    private HashMap<Integer,Integer> mostRentedMovie = new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer> mostActiveClient = new HashMap<Integer,Integer>();
    private ArrayList<String> rentalOfMostActive = new ArrayList<String>();

    public RentalController(Repository<Integer, RentAction> repo, ClientController initClientController,MovieController initMovieController) {
        this.repo = repo;
        validator = new RentValidator();
        clientController = initClientController;
        movieController = initMovieController;
    }

    public Set<RentAction> getAllRentals() throws SQLException {
        Iterable<RentAction> rentals = repo.findAll();
        return (Set<RentAction>) rentals;
    }

    public void addRental(RentAction rentalToAdd) throws ValidatorException {
        try {

            int clientID = rentalToAdd.getClientId();
            int movieID = rentalToAdd.getMovieId();

            Optional<Client> c = clientController.getById(clientID);
            Optional<Movie> m = movieController.getById(movieID);

            if(!c.isPresent())
                throw new ValidatorException("Client does not exist");

            if(!m.isPresent())
                throw new ValidatorException("Movie does not exist");

            validator.validate(rentalToAdd);

            repo.save(rentalToAdd);
            updateReports(rentalToAdd);

        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());}
        catch (RentalException r){
            throw new RentalException(r.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRental(RentAction rent) {
        try{

            int clientID = rent.getClientId();
            int movieID = rent.getMovieId();

            Optional<Client> c = clientController.getById(clientID);
            Optional<Movie> m = movieController.getById(movieID);

            if(!c.isPresent())
                throw new ValidatorException("Client does not exist");

            if(!m.isPresent())
                throw new ValidatorException("Movie does not exist");

            validator.validate(rent);
            repo.update(rent);
            updateReports(rent);

        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());}
        catch (RentalException r){
                throw new RentalException(r.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Integer> getMostActiveClient()
    {

        Map<Integer, Integer> mostActive = mostActiveClient.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        System.out.println(mostActive);

        return new ArrayList<>(mostActive.keySet());
    }

    public List<Integer> getMostRentedMovie()
    {
        Map<Integer, Integer> mostRented = mostRentedMovie.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        System.out.println(mostRented);

        return new ArrayList<>(mostRented.keySet());

    }

    public List<String> getRentedMoviesOfMostActiveClient() throws SQLException {
        List<Integer> mostActive = getMostActiveClient();
        int clientId = mostActive.get(mostActive.size()-1);
        Set<String> all =  ((Set<RentAction>)repo.findAll()).stream()
                .filter(e->e.getClientId()==clientId)
                .map(ra->movieController.getById(ra.getMovieId()))
                .filter(Optional::isPresent)
                .map(o->o.get().getTitle())
                .collect(Collectors.toSet());

        return new ArrayList<String>(all);
    }

    public void updateTheReports() throws SQLException {
        mostRentedMovie = new HashMap<Integer,Integer>();
        mostActiveClient = new HashMap<Integer,Integer>();
        rentalOfMostActive = new ArrayList<String>();
        Set<RentAction> rents = (Set<RentAction>) repo.findAll();
        for(RentAction r: rents){
            try{
                updateReports(r);
            }
            catch (SQLException e){
                throw new SQLException();
            }
        }
    }

    private void updateReports(RentAction rentalToAdd) throws SQLException {
        int clientKey = rentalToAdd.getClientId();
        int movieKey = rentalToAdd.getMovieId();
        int clientAmount=0,movieAmount=0;
        if(mostActiveClient.containsKey(clientKey))
        {
            clientAmount = mostActiveClient.get(clientKey);
            mostActiveClient.replace(clientKey, clientAmount + 1);
        }
        else
            mostActiveClient.putIfAbsent(clientKey,1);

        if(mostRentedMovie.containsKey(movieKey))
        {
            movieAmount = mostRentedMovie.get(movieKey);
            mostRentedMovie.replace(movieKey, movieAmount + 1);
        }
        else
            mostRentedMovie.putIfAbsent(movieKey,1);

         rentalOfMostActive = (ArrayList<String>) getRentedMoviesOfMostActiveClient();
    }


    public void deleteRent(Integer rentToDelete) throws ValidatorException{
        try{
            repo.delete(rentToDelete);
        }
        catch (ValidatorException v){
            throw  new ValidatorException((v.getMessage()));
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(Integer clientToDelete) throws ValidatorException, SQLException {
        clientController.deleteClient(clientToDelete);
        Iterable<RentAction> rentals = repo.findAll();
        rentals.forEach(Rent->{
            if(Rent.getClientId() == clientToDelete){
                this.deleteRent(Rent.getRentId());
            }
        });
    }

    public void deleteMovie(Integer movieToDelete) throws ValidatorException, SQLException {
        movieController.deleteMovie(movieToDelete);
        Iterable<RentAction> rentals = repo.findAll();
        rentals.forEach(Rent->{
            if(Rent.getMovieId() == movieToDelete){
                this.deleteRent(Rent.getRentId());
            }
        });
    }



}
