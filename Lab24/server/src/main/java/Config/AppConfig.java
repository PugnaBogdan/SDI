package Config;

import Controller.ClientController;
import Controller.ClientService;
import Entities.Client;
import Entities.Movie;
import Entities.Validators.ClientValidator;
import Repository.Repository;
import Repository.SpringDB.ClientSpringDBRepo;
import Repository.SpringDB.MovieSpringDBRepo;
import ServerController.ServerControllerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
public class AppConfig {


    @Bean(name = "ClientService")
    RmiServiceExporter rmiServiceExporterClient() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ClientService");
        rmiServiceExporter.setServiceInterface(ClientService.class);
        rmiServiceExporter.setService(serverClientService());
        rmiServiceExporter.setRegistryPort(1099);
        return rmiServiceExporter;
    }


    @Bean
    ClientService serverClientService(){
        return new ServerControllerClient();
    }

    @Bean
    ClientController clientController()
    {
        return new ClientController() ;
    }

    @Bean
    ClientSpringDBRepo repo(){
        return new ClientSpringDBRepo();
    }


    @Bean
    ClientValidator validator() {return new ClientValidator();}



}
