package webModulePackage.controller;

import coreModulePackage.Controller.ClientService;
import coreModulePackage.Entities.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webModulePackage.converter.ClientConverter;
import webModulePackage.dto.EntitiesDTO.ClientDto;
import webModulePackage.dto.ListsDTO.ClientsDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ClientController {

    public static final Logger log= LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<ClientDto> getStudents() {
        log.trace("get Clients");

        List<Client> students = clientService.getAllClients();

        log.trace("getClients: clients={}", students);

        return new ArrayList<>(clientConverter.convertModelsToDtos(students));
    }


    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto addClient(@RequestBody ClientDto clientDto) {
        log.trace("addClient - method entered - web");
        return clientConverter.convertModelToDto(clientService.addClient(
                clientConverter.convertDtoToModel(clientDto)
        ));
    }

    @CrossOrigin
    @RequestMapping(value = "/clients", method = RequestMethod.PUT)
    ClientDto updateClient(@RequestBody ClientDto clientDto) throws Exception
    {

        log.trace("updateClient - method entered - web");
        return clientConverter.convertModelToDto( clientService.updateClient(
                clientConverter.convertDtoToModel(clientDto)));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Integer id){


        log.trace("deleteClient - method entered - web");
        clientService.deleteClient(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/clients/filter", method = RequestMethod.GET)
    ClientsDto filterOddId() throws Exception {


        log.trace("filterOddClients - method entered - web");
        return new  ClientsDto(clientConverter.convertModelsToDtos(clientService.filterOddId()));
    }

    @RequestMapping(value = "/clients/filter/{length}", method = RequestMethod.GET)
    ClientsDto filterClientsWithNameLessThan(@PathVariable Integer length) throws Exception {


        log.trace("filterClientsWithNameLessThan - method entered - web");
        return new  ClientsDto(clientConverter.convertModelsToDtos(clientService.filterClientsWithNameLessThan(length)));
    }



}
