package org.example.controller;

import org.example.model.Flight;
import org.example.persistance.jdbc.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@CrossOrigin(origins = "http://localhost:4200")
public class FlightController {

    private FlightRepository flightRepository;

    @Autowired
    public void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Flight> getAll(){
        System.out.println("Get all flights ...");
        List<Flight> flights = (List<Flight>)flightRepository.findAll();
        return flights;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Long id){
        System.out.println("Get by id "+id);
        Flight flight = flightRepository.findOne(id);
        if (flight==null)
            return new ResponseEntity<>("Flight not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id){
        System.out.println("Deleting flight ... "+id);
        Flight flight = flightRepository.delete(id);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Flight flight){
        System.out.println("Creating flight ...");
        Flight flight1 = flightRepository.save(flight);
        if (flight1 == null)
            return new ResponseEntity<>("Error while adding flight", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(flight1, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Flight flight){
        System.out.println("Updating flight ...");
        flight.setId(id);
        Flight flight1 = flightRepository.update(flight);
        if (flight1 == null)
            return new ResponseEntity<>("Error while updating flight", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(flight1, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(Exception e) {
        return e.getMessage();
    }
}
