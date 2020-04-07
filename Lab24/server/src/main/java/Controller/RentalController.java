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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class RentalController implements RentalService{

    private Repository<Integer, RentAction> repo;
    private RentValidator validator;
    private ClientService clientController;
    private MovieService movieController;
    private HashMap<Integer,Integer> mostRentedMovie = new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer> mostActiveClient = new HashMap<Integer,Integer>();
    private List<String> rentalOfMostActive = new ArrayList<String>();
    private ExecutorService executorService;

    public RentalController(Repository<Integer, RentAction> repo, ClientService initClientController, MovieService initMovieController, ExecutorService executorService) {
        this.repo = repo;
        validator = new RentValidator();
        clientController = initClientController;
        movieController = initMovieController;
        this.executorService = executorService;
    }


    public CompletableFuture<Set<RentAction>> getAllRentals() throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            Iterable<RentAction> rentals = null;
            try {
                rentals = repo.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return (Set<RentAction>) rentals;
        },executorService);
    }

    public CompletableFuture<Void> addRental(RentAction rentalToAdd) throws ValidatorException {
        return CompletableFuture.supplyAsync(()-> {
            try {

                int clientID = rentalToAdd.getClientId();
                int movieID = rentalToAdd.getMovieId();

                Optional<Client> c = clientController.getById(clientID);
                Optional<Movie> m = movieController.getById(movieID);

                if (c.isEmpty())
                    throw new ValidatorException("Client does not exist");

                if (m.isEmpty())
                    throw new ValidatorException("Movie does not exist");

                validator.validate(rentalToAdd);

                repo.save(rentalToAdd);
                updateReports(rentalToAdd);
                return null;

            } catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
            {
                throw new ValidatorException(v.getMessage());
            }

        },executorService);
    }

    public CompletableFuture<Void> updateRental(RentAction rent) {
        return CompletableFuture.supplyAsync(()-> {
            try {

                int clientID = rent.getClientId();
                int movieID = rent.getMovieId();

                Optional<Client> c = clientController.getById(clientID);
                Optional<Movie> m = movieController.getById(movieID);

                if (!c.isPresent())
                    throw new ValidatorException("Client does not exist");

                if (!m.isPresent())
                    throw new ValidatorException("Movie does not exist");

                validator.validate(rent);
                repo.update(rent);
                updateReports(rent);

            } catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
            {
                throw new ValidatorException(v.getMessage());
            }
            return null;
        },executorService);
    }


    public CompletableFuture<List<Integer>> getMostActiveClient() throws SQLException {
        updateTheReports();
        return CompletableFuture.supplyAsync(()-> {
            Map<Integer, Integer> mostActive = mostActiveClient.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));

            System.out.println(mostActive);

            return new ArrayList<>(mostActive.keySet());
        });
    }

    public CompletableFuture<List<Integer>> getMostRentedMovie() throws SQLException {
        updateTheReports();
        return CompletableFuture.supplyAsync(()-> {
            Map<Integer, Integer> mostRented = mostRentedMovie.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                            LinkedHashMap::new));

            System.out.println(mostRented);

            return new ArrayList<>(mostRented.keySet());
        });

    }

    public CompletableFuture<List<String>> getRentedMoviesOfMostActiveClient() throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            List<Integer> mostActive = null;
            try {
                mostActive = (List<Integer>) getMostActiveClient();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int clientId = mostActive.get(mostActive.size() - 1);
            Set<String> all = null;
            try {
                all = ((Set<RentAction>) repo.findAll()).stream()
                        .filter(e -> e.getClientId() == clientId)
                        .map(ra -> {
                            try {
                                return movieController.getById(ra.getMovieId());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .filter(Optional::isPresent)
                        .map(o -> o.get().getTitle())
                        .collect(Collectors.toSet());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return new ArrayList<String>(all);
        });
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

            for(RentAction r: rents)
            {
                try {
                    updateReports(r);
                }

                catch (SQLException e) {
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

         rentalOfMostActive =  getRentedMoviesOfMostActiveClient();
    }


    public CompletableFuture<Void> deleteRent(Integer rentToDelete) throws ValidatorException{
        return CompletableFuture.supplyAsync(()-> {
            try {
                repo.delete(rentToDelete);
            } catch (ValidatorException v) {
                throw new ValidatorException((v.getMessage()));
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }
            return null;
        },executorService);
    }

    /// THEY DONT MATTER ANYMORE ----------------------------------
    public CompletableFuture<Void> deleteClientCascade(int clientToDelete) throws ValidatorException, SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                clientController.deleteClient(clientToDelete);
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }
            Iterable<RentAction> rentals = null;
            try {
                rentals = repo.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rentals.forEach(Rent -> {
                if (Rent.getClientId() == clientToDelete) {
                    this.deleteRent(Rent.getRentId());
                }
            });
            return null;
        },executorService);
    }

    public CompletableFuture<Void> deleteMovieCascade(int movieToDelete) throws ValidatorException, SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        return CompletableFuture.supplyAsync(()-> {
            try {
                movieController.deleteMovie(movieToDelete);
                Iterable<RentAction> rentals = null;
                rentals = repo.findAll();
                rentals.forEach(Rent -> {
                    if (Rent.getMovieId() == movieToDelete) {
                        this.deleteRent(Rent.getRentId());
                    }
                });
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }

            return null;
        },executorService);
    }



}
