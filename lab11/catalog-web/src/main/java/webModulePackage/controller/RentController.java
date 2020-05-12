package webModulePackage.controller;

import coreModulePackage.Controller.RentalService;
import coreModulePackage.Entities.Movie;
import coreModulePackage.Entities.RentAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webModulePackage.converter.RentConverter;
import webModulePackage.dto.EntitiesDTO.MovieDto;
import webModulePackage.dto.EntitiesDTO.RentDto;
import webModulePackage.dto.ListsDTO.RentsDto;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentController {

    public static final Logger log= LoggerFactory.getLogger(RentController.class);

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentConverter rentConverter;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public List<RentDto> getRents() {
        log.trace("getRents");

        List<RentAction> rentals = rentalService.getAllRentals();

        log.trace("getRents: rents={}", rentals);

        return new ArrayList<>(rentConverter.convertModelsToDtos(rentals));
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    RentDto addRent(@RequestBody RentDto rentDto) throws Exception {


        log.trace("addRent - method entered - web");
        return rentConverter.convertModelToDto(rentalService.addRental(
                rentConverter.convertDtoToModel(rentDto)
        ));
    }

    @RequestMapping(value = "/rentals", method = RequestMethod.PUT)
    RentDto updateClient(@RequestBody RentDto rentDto) throws Exception
    {

        log.trace("updateRent - method entered - web");
        return rentConverter.convertModelToDto( rentalService.updateRental(
                rentConverter.convertDtoToModel(rentDto)));
    }

    @RequestMapping(value = "/rentals/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRent(@PathVariable Integer id){

        log.trace("deleteRent - method entered - web");
        rentalService.deleteRent(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
