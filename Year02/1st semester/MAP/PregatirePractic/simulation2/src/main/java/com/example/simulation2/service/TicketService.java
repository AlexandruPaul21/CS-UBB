package com.example.simulation2.service;

import com.example.simulation2.domain.Ticket;
import com.example.simulation2.repository.TicketDbRepository;

public class TicketService {
    private TicketDbRepository ticketDbRepository;

    public TicketService(TicketDbRepository ticketDbRepository) {
        this.ticketDbRepository = ticketDbRepository;
    }

    public Ticket save(Ticket ticket) {
        return ticketDbRepository.save(ticket);
    }
}
