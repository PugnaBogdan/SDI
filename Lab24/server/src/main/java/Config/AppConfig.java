package Config;

import Controller.*;
import Entities.Client;
import Entities.Movie;
import Entities.Validators.ClientValidator;
import Entities.Validators.MovieValidator;
import Entities.Validators.RentValidator;
import Repository.Repository;
import Repository.SpringDB.ClientSpringDBRepo;
import Repository.SpringDB.MovieSpringDBRepo;
import Repository.SpringDB.RentalSpringDBRepo;
import ServerController.ServerControllerClient;
import ServerController.ServerControllerMovie;
import ServerController.ServerControllerRents;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class AppConfig {


    @Bean
    RmiServiceExporter rmiServiceExporterClient() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ClientService");
        rmiServiceExporter.setServiceInterface(ClientService.class);
        rmiServiceExporter.setService(clientServiceServer());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiServiceExporterMovie() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("MovieService");
        rmiServiceExporter.setServiceInterface(MovieService.class);
        rmiServiceExporter.setService(movieServiceServer());
        return rmiServiceExporter;
    }
    @Bean
    RmiServiceExporter rmiServiceExporterRents() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("RentalService");
        rmiServiceExporter.setServiceInterface(RentalService.class);
        rmiServiceExporter.setService(rentalServiceServer());
        return rmiServiceExporter;
    }

    @Bean
    RentalService rentalServiceServer() {
        return new ServerControllerRents();
    }

    @Bean
    MovieService movieServiceServer() {
        return new ServerControllerMovie();
    }


    @Bean
    ClientService clientServiceServer(){
        return new ServerControllerClient();
    }


    //client
    @Bean
    ClientController clientController()
    {
        return new ClientController() ;
    }

    @Bean
    ClientSpringDBRepo clientRepo(){
        return new ClientSpringDBRepo();
    }

    @Bean
    ClientValidator clientValidator() {return new ClientValidator();}

    //movie

    @Bean
    MovieController movieController(){ return new MovieController();}
    @Bean
    MovieSpringDBRepo movieRepo(){return new MovieSpringDBRepo();}
    @Bean
    MovieValidator movieValidator(){return new MovieValidator();}

    //rents
    @Bean
    RentalController rentalController(){return new RentalController();}
    @Bean
    RentalSpringDBRepo rentalRepo(){return new RentalSpringDBRepo();}
    @Bean
    RentValidator rentValidator(){return new RentValidator();}

}
