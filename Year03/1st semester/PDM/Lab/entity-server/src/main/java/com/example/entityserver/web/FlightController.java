package com.example.entityserver.web;

import com.example.entityserver.model.dto.FlightDto;
import com.example.entityserver.model.dto.WsEvent;
import com.example.entityserver.service.FlightService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(
    value = "io.swagger.codegen.languages.SpringCodegen"
)
@RestController
@CrossOrigin(origins = "http://localhost:8100")
@Api("Flight API")
public class FlightController {
    private FlightService flightService;

    @Autowired
    public void setFlightService(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/api/flights", method = RequestMethod.GET)
    public ResponseEntity<List<FlightDto>> getFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @RequestMapping(value = "/api/flights/{id}", method = RequestMethod.GET)
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @RequestMapping(value = "/api/flights/{id}", method = RequestMethod.DELETE)
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
    }

    @RequestMapping(value = "/api/flights", method = RequestMethod.POST)
    public ResponseEntity<FlightDto> saveFlight(@RequestBody FlightDto flight) {
        return ResponseEntity.ok(flightService.save(flight));
    }

    @RequestMapping(value = "/api/flights/{id}", method = RequestMethod.PUT)
    public ResponseEntity<FlightDto> updateFlight(@PathVariable Long id,@RequestBody FlightDto flight) {
        flight.setId(id);
        return ResponseEntity.ok(flightService.save(flight));
    }

    @MessageMapping("/ws")
    @SendTo("/topic/flights")
    public WsEvent sendEvent(WsEvent event) {
        return event;
    }
}
