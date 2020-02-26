package dev.lcastrooliveira.ditoproducer.controllers;

import dev.lcastrooliveira.ditoproducer.services.EventService;
import dtos.EventDocumentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/events")
@Slf4j
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public void publishEvent(@NotNull @Valid @RequestBody EventDocumentDTO event) {
        eventService.publishEvent(event);
    }
}
