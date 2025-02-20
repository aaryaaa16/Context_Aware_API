package com.example.demo.controller;

import com.example.demo.model.request.CreateRequest;
import com.example.demo.model.request.DisplayRequest;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.model.tables.IdentityTable;
import com.example.demo.repository.IdentityRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

import static com.example.demo.util.Strings.IDENTITY_APP_ID;

@RestController
@RequestMapping(value = "/api/identity", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
public class IdentityController {
    @Autowired
    IdentityRepository identityRepository;


    @PostMapping(value = "/create")
    private String createId(@RequestBody CreateRequest createRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (Objects.equals(createRequest.getAccessId(), IDENTITY_APP_ID)) {
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
    private String displayId(@RequestBody DisplayRequest displayRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try {
            if (Objects.equals(displayRequest.getAccessId(), IDENTITY_APP_ID)) {
                Optional<IdentityTable> identityOptional = identityRepository.findByIndex(displayRequest.getIndex());
                if (identityOptional.isPresent()) {
                    IdentityTable identityTable = identityOptional.get();
                    messageResponse.setMessage(identityTable.getData());
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
        IdentityTable identityTable = new IdentityTable();
        identityTable.setIndex(createRequest.getIndex());
        identityTable.setData(createRequest.getData());
        identityRepository.save(identityTable);
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
