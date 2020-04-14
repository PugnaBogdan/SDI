package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.RentValidator;
import Entities.Validators.RentalException;
import Entities.Validators.ValidatorException;
import Repository.Repository;
import Repository.SpringDB.ClientSpringDBRepo;
import Repository.SpringDB.MovieSpringDBRepo;
import Repository.SpringDB.RentalSpringDBRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class RentalController implements RentalService{


    @Autowired
    private RentalSpringDBRepo rentalRepo;
    @Autowired
    private RentValidator rentValidator;
    @Autowired
    private MovieSpringDBRepo movieSpringDBRepo;
    @Autowired
    private ClientSpringDBRepo clientSpringDBRepo;


    private HashMap<Integer,Integer> mostRentedMovie = new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer> mostActiveClient = new HashMap<Integer,Integer>();
    private List<String> rentalOfMostActive = new ArrayList<String>();



    public Set<RentAction> getAllRentals() throws SQLException {
        Iterable<RentAction> movies = rentalRepo.findAll();
        Set<RentAction> set = new HashSet<>();
        movies.forEach(set::add);
        return set;
    }

    public void addRental(RentAction rentalToAdd) throws ValidatorException {
            try {

                int clientID = rentalToAdd.getClientId();
                int movieID = rentalToAdd.getMovieId();

                Optional<Client> c = clientSpringDBRepo.findOne(clientID);
                Optional<Movie> m = movieSpringDBRepo.findOne(movieID);

                if (c.isEmpty())
                    throw new ValidatorException("Client does not exist");

                if (m.isEmpty())
                    throw new ValidatorException("Movie does not exist");

                rentValidator.validate(rentalToAdd);

                rentalRepo.save(rentalToAdd);
                updateReports(rentalToAdd);


            }
            catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
            {
                throw new ValidatorException(v.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

    }

    public void updateRental(RentAction rent) {
            try {

                int clientID = rent.getClientId();
                int movieID = rent.getMovieId();

                Optional<Client> c = clientSpringDBRepo.findOne(clientID);
                Optional<Movie> m = movieSpringDBRepo.findOne(movieID);

                if (c.isEmpty())
                    throw new ValidatorException("Client does not exist");

                if (m.isEmpty()) {
                    throw new ValidatorException("Movie does not exist");
                }

                rentValidator.validate(rent);
                rentalRepo.update(rent);
                updateReports(rent);

            } catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
            {
                throw new ValidatorException(v.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
    }


    public List<Integer> getMostActiveClient() throws SQLException {
        updateTheReports();
            Map<Integer, Integer> mostActive = mostActiveClient.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));

            //System.out.println(mostActive);

            return new ArrayList<>(mostActive.keySet());
    }

    public List<Integer> getMostRentedMovie() throws SQLException {
        updateTheReports();
            Map<Integer, Integer> mostRented = mostRentedMovie.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));

            //System.out.println(mostRented);

            return new ArrayList<>(mostRented.keySet());

    }

    public List<String> getRentedMoviesOfMostActiveClient() throws SQLException {

            List<Integer> mostActive = null;
            try {
                mostActive = new ArrayList<>(getMostActiveClient());

            } catch (SQLException e) {
                e.printStackTrace();
            }
            int clientId = mostActive.get(mostActive.size() - 1);
            Set<String> all = null;
            Set<RentAction > x = getAllRentals();

        all = x.stream()
                .filter(e -> e.getClientId() == clientId)
                .map(ra -> {
                    try {
                        System.out.println(ra.getMovieId());
                        return movieSpringDBRepo.findOne(ra.getMovieId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .filter(Optional::isPresent)
                .map(o -> o.get().getTitle())
                .collect(Collectors.toSet());
        return new ArrayList<String>(all);
    }


    public void updateTheReports() throws SQLException {
        mostRentedMovie = new HashMap<Integer,Integer>();
        mostActiveClient = new HashMap<Integer,Integer>();
        rentalOfMostActive = new ArrayList<String>();
        Iterable<RentAction> rents = rentalRepo.findAll();
        for(RentAction r: rents){
            try{
                updateReports(r);
            }
            catch (SQLException | ExecutionException | InterruptedException e){
                throw new SQLException();
            }
        }

        for(RentAction r: rents)
        {
            try {
                updateReports(r);
            }

            catch (SQLException | ExecutionException | InterruptedException e) {
                throw new SQLException();
            }
        }
    }

    private void updateReports(RentAction rentalToAdd) throws SQLException, ExecutionException, InterruptedException {
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
    }


    public void deleteRent(Integer rentToDelete) throws ValidatorException{
            try {
                rentalRepo.delete(rentToDelete);
            } catch (ValidatorException v) {
                throw new ValidatorException((v.getMessage()));
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }
    }

}
