package clientModulePackage.Ui;

import coreModulePackage.Entities.Client;
import coreModulePackage.Entities.Movie;
import coreModulePackage.Validators.RentalException;
import coreModulePackage.Validators.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import webModulePackage.dto.EntitiesDTO.ClientDto;
import webModulePackage.dto.EntitiesDTO.MovieDto;
import webModulePackage.dto.EntitiesDTO.RentDto;
import webModulePackage.dto.ListsDTO.ClientsDto;
import webModulePackage.dto.ListsDTO.MoviesDto;
import webModulePackage.dto.ListsDTO.RentsDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Rares2.
 */
@Component
public class UserInterface {
    private RestTemplate restTemplate;
    public static final String MainURL = "http://localhost:8080/api";
    public UserInterface(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public void run() throws Exception {
        while (true) {
            System.out.println("Commands: ");
            System.out.println("Client: ");
            System.out.println(" 1 - add");
            System.out.println(" 2 - show");
            System.out.println(" 3 - Update");
            System.out.println(" 4 - delete ");
            System.out.println("");
            System.out.println("Movie: ");
            System.out.println(" 5 - add");
            System.out.println(" 6 - show");
            System.out.println(" 7 - Update");
            System.out.println(" 8 - delete");
            System.out.println("");
            System.out.println("Rent: ");
            System.out.println(" 9 - add ");
            System.out.println(" 10 - show ");
            System.out.println(" 11 - Update");
            System.out.println(" 12 - delete");
            System.out.println(" ");
            System.out.println(" 13 - filter client by odd Id");
            System.out.println(" 14 - filter movie by title length");
            System.out.println(" 15 - filter movie by even ID");
            System.out.println(" 16 - filter client by name length");
            System.out.println(" 0 - exit");
            System.out.println("Input command: ");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

            try {
                int command = Integer.parseInt(bufferRead.readLine());
                if (command == 1) {
                    addClient();
                } else if (command == 2) {
                    printAllClients();
                } else if (command == 3) {
                    updateClient();
                } else if (command == 4) {
                    deleteClient();
                } else if (command == 5) {
                    addMovie();
                } else if (command == 6) {
                    printAllMovies();
                } else if (command == 7) {
                    updateMovie();
                } else if (command == 8) {
                    deleteMovie();
                } else if (command == 9) {
                    addRent();
                } else if (command == 10) {
                    printAllRents();
                } else if (command == 11) {
                    updateRental();
                } else if (command == 12) {
                    deleteRent();
                } else if (command == 13) {
                    filterOddIdClient();
                } else if (command == 14) {
                    filterMovieByNameLength();
                } else if (command == 15) {
                    filterEvenMovie();
                } else if (command == 16) {
                    filterClientByNameLength();
                } else break;

            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void printAllClients() throws Exception {
        ClientsDto allClients = restTemplate.getForObject(MainURL+"/clients", ClientsDto.class);
        List<ClientDto> list = allClients.getClientDtos();
        list.forEach(System.out::println);
    }

    private void printAllMovies() throws Exception {

        MoviesDto allMovies = restTemplate.getForObject(MainURL+"/movies",MoviesDto.class);
        List<MovieDto> list = allMovies.getMovieDtos();
        list.forEach(System.out::println);

    }

    private void printAllRents() throws SQLException {

        RentsDto allRents = restTemplate.getForObject(MainURL+"/rents",RentsDto.class);
        List<RentDto> list = allRents.getRentsDtos();
        list.forEach(System.out::println);
    }


    private void addClient() {

        ClientDto client = readClient();
        try {
            ClientDto clientToSave = restTemplate.postForObject(
                    MainURL+"/clients",
                    client,
                    ClientDto.class);
            System.out.println("Client" + clientToSave.toString() + "was added");
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

    }

    private void addMovie() {

        MovieDto movie = readMovie();
        try {
            MovieDto movieToSave = restTemplate.postForObject(MainURL+"/movies",movie,MovieDto.class);

            System.out.println("Movie" + movieToSave.toString() + "was added");
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

    }

    private void addRent() throws Exception {

        RentDto rent = readRent();
        try {
            RentDto rentSave = restTemplate.postForObject(MainURL+"/rents",rent,RentDto.class);
            System.out.println("Rent" + rentSave.toString() + "was added");

        } catch (RentalException e) {
            System.out.println(e.getMessage());
        }

    }

    private ClientDto readClient() {
        System.out.println("Read client {name, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String name = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());// ...

            return new ClientDto(name, age);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private MovieDto readMovie() {
        System.out.println("Read movie {title, price}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            String title = bufferRead.readLine();
            int price = Integer.parseInt(bufferRead.readLine());// ...

            return new MovieDto( title, price);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private RentDto readRent() {
        System.out.println("Read rental {clientId, movieId}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {

            int id1 = Integer.parseInt(bufferRead.readLine());// ...
            int id2 = Integer.parseInt(bufferRead.readLine());// ...

            return new RentDto(id1, id2);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * deletes a client
     */
    private void deleteClient() {
        System.out.println("Type client id you want to delete: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            int id = Integer.parseInt(bufferRead.readLine());

            restTemplate.delete(MainURL+"/clients/{id}",id);

        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    private void deleteMovie() {
        System.out.println("Type movie id you want to delete: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {

            int id = Integer.parseInt(bufferRead.readLine());
            restTemplate.delete(MainURL+"/movies/{id}",id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteRent() {
        System.out.println("Type rent id you want to delete: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try {
            int id = Integer.parseInt(bufferRead.readLine());

            restTemplate.delete(MainURL+"/rents/{id}",id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Client updateClient() {
        System.out.println("Read new clients attributes {id,name, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            String name = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());// ...

            ClientDto newCLient = new ClientDto(name, age);
            newCLient.setId(id);

            restTemplate.put(MainURL+"/clients" , newCLient);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    private Movie updateMovie() throws Exception {
        System.out.println("Read new movie attributes {id,title,price}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            String title = bufferRead.readLine();
            int price = Integer.parseInt(bufferRead.readLine());// ...
            MovieDto movie = new MovieDto(title, price);
            movie.setId(id);

            restTemplate.put(MainURL+"/movies",movie);


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    private Client updateRental() {
        System.out.println("Read new rental {id,clientId, movieId}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            int id1 = Integer.parseInt(bufferRead.readLine());// ...
            int id2 = Integer.parseInt(bufferRead.readLine());// ...
            RentDto rent = new RentDto(id1, id2);
            rent.setId(id);

            restTemplate.put(MainURL+"/rents",rent);

        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    private void filterOddIdClient() throws Exception {
        ClientsDto allClients = restTemplate.getForObject(MainURL+"/clients/filter", ClientsDto.class);
        assert allClients != null;
        List<ClientDto> list = allClients.getClientDtos();
        list.forEach(System.out::println);

    }

    private void filterEvenMovie() throws Exception {
       MoviesDto allMovies = restTemplate.getForObject(MainURL+"/movies/filter", MoviesDto.class);
       assert allMovies !=null;
       List<MovieDto> list = allMovies.getMovieDtos();
       list.forEach(System.out::println);
    }

    private void filterMovieByNameLength() throws Exception {
        System.out.println("Type title length: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));


        try {
            int length = Integer.parseInt(bufferRead.readLine());//
            MoviesDto allClients = restTemplate.postForObject(MainURL+"/movies/filter",length,MoviesDto.class);
            List<MovieDto> list = allClients.getMovieDtos();
            list.forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void filterClientByNameLength() throws Exception {
        System.out.println("Type name length: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int length = Integer.parseInt(bufferRead.readLine());// ...
            ClientsDto allClients = restTemplate.getForObject(MainURL+"/clients/filter/{length}",ClientsDto.class,length);
            List<ClientDto> list = allClients.getClientDtos();
            list.forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}


