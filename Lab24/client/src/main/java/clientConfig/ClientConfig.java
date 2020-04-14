package clientConfig;


import Controller.ClientService;
import Controller.MovieService;
import Controller.RentalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientConfig {

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanClient() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(ClientService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ClientService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanMovie() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(MovieService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/MovieService");
        return rmiProxyFactoryBean;
    }
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanRents() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(RentalService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/RentalService");
        return rmiProxyFactoryBean;
    }






}
