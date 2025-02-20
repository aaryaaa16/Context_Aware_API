package com.example.demo.controller;

import com.example.demo.model.request.CreateRequest;
import com.example.demo.model.request.DisplayRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.model.tables.HealthcareTable;
import com.example.demo.repository.HealthcareRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

import static com.example.demo.util.Strings.HEALTHCARE_APP_ID;

@RestController
@RequestMapping(value = "/api/healthcare", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
public class HealthcareController {

    @Autowired
    HealthcareRepository healthcareRepository;


    @PostMapping(value = "/create")
    private String createRecord(@RequestBody CreateRequest createRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (Objects.equals(createRequest.getAccessId(), HEALTHCARE_APP_ID)) {
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
    private String displayRecord(@RequestBody DisplayRequest displayRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (Objects.equals(displayRequest.getAccessId(), HEALTHCARE_APP_ID)) {
                Optional<HealthcareTable> healthcareOptional = healthcareRepository.findByIndex(displayRequest.getIndex());
                if (healthcareOptional.isPresent()) {
                    HealthcareTable healthcareTable = healthcareOptional.get();
                    messageResponse.setMessage(healthcareTable.getData());
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
        HealthcareTable healthcareTable = new HealthcareTable();
        healthcareTable.setIndex(createRequest.getIndex());
        healthcareTable.setData(createRequest.getData());
        healthcareRepository.save(healthcareTable);
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
