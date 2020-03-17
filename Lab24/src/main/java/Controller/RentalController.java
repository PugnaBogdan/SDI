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
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;

public class RentalController {

    private Repository<Integer, RentAction> repo;
    private RentValidator validator;
    private ClientController clientController;
    private MovieController movieController;
    private HashMap<Integer,Integer> mostRentedMovie = new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer> mostActiveClient = new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer> repeatedRentals = new HashMap<Integer,Integer>();

    public RentalController(Repository<Integer, RentAction> repo, ClientController initClientController,MovieController initMovieController) {
        this.repo = repo;
        validator = new RentValidator();
        clientController = initClientController;
        movieController = initMovieController;
    }

    public Set<RentAction> getAllRentals() {
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

            for(RentAction rent : repo.findAll())
                if(rent.getMovieId()==rentalToAdd.getMovieId())
                    throw new RentalException("Movie already rented!");
            validator.validate(rentalToAdd);

            repo.save(rentalToAdd);
            updateReports(rentalToAdd);

        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());}
        catch (RentalException r){
            throw new RentalException(r.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
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
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }


    public List<Integer> getMostActiveClient()
    {

        Map<Integer, Integer> mostActive = mostActiveClient.entrySet()
                .stream()
                .sorted((a,b)->a.getValue().compareTo(b.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



        System.out.println(mostActive);

        List<Integer> al = new ArrayList<Integer>(mostActive.keySet());

        return al;
    }

    public List<Integer> getMostRentedMovie()
    {
        Map<Integer, Integer> mostRented = mostRentedMovie.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Integer> al = new ArrayList<Integer>(mostRented.keySet());

        return al;

    }

    public HashMap<Integer,Integer> getRepeatedRentals()
    {
        return this.repeatedRentals;
    }

    private void updateReports(RentAction rentalToAdd)
    {
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

        if(repeatedRentals.containsKey(rentalToAdd.getRentId()))
            repeatedRentals.replace(rentalToAdd.getRentId(),repeatedRentals.get(rentalToAdd.getRentId())+1);
        else
            repeatedRentals.putIfAbsent(rentalToAdd.getRentId(),1);
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

}
