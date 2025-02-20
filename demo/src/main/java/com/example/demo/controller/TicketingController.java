package com.example.demo.controller;

import com.example.demo.model.request.CreateRequest;
import com.example.demo.model.request.DisplayRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.model.tables.TicketTable;
import com.example.demo.repository.TicketingRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

import static com.example.demo.util.Strings.TICKETING_APP_ID;

@RestController
@RequestMapping(value = "/api/ticketing", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
public class TicketingController {

    @Autowired
    TicketingRepository ticketingRepository;


    @PostMapping(value = "/create")
    private String createTicket(@RequestBody CreateRequest createRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (Objects.equals(createRequest.getAccessId(), TICKETING_APP_ID)) {
                createObject(createRequest);
                messageResponse.setMessage("New record created");
            } else {
                messageResponse.setMessage("Access Denied");
                return convertResponseToXML(messageResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return convertResponseToXML(messageResponse);
    }

    @PostMapping(value = "/display")
    private String displayTicket(@RequestBody DisplayRequest displayRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (Objects.equals(displayRequest.getAccessId(), TICKETING_APP_ID)) {
                Optional<TicketTable> ticketOptional = ticketingRepository.findByIndex(displayRequest.getIndex());
                if (ticketOptional.isPresent()) {
                    TicketTable ticketTable = ticketOptional.get();
                    messageResponse.setMessage(ticketTable.getData());
                } else {
                    messageResponse.setMessage("Identity not found for Id: " + displayRequest.getIndex());
                    convertResponseToXML(messageResponse);
                }
            } else {
                messageResponse.setMessage("Access Denied");
                return convertResponseToXML(messageResponse);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return convertResponseToXML(messageResponse);
    }

    private void createObject(CreateRequest createRequest) {
        TicketTable ticketTable = new TicketTable();
        ticketTable.setIndex(createRequest.getIndex());
        ticketTable.setData(createRequest.getData());
        ticketingRepository.save(ticketTable);
    }

    private static <T> String convertResponseToXML(T responseObject) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(responseObject);
        } catch (Exception e) {
            return "<error>XML Conversion Failed</error>";
        }
    }
}
