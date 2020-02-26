package dev.lcastrooliveira.ditoproducer.services;

import dtos.EventDocumentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class EventService {

    private final KafkaTemplate<String, EventDocumentDTO> kafkaTemplate;

    @Autowired
    public EventService(KafkaTemplate<String, EventDocumentDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(EventDocumentDTO event) {
        log.info("Sending event {} to topic {}", event.toString(), "events");
        final ListenableFuture<SendResult<String, EventDocumentDTO>> future = kafkaTemplate.send("events", event);
        future.addCallback(new ListenableFutureCallback<SendResult<String, EventDocumentDTO>>() {
            @Override
            public void onSuccess(SendResult<String, EventDocumentDTO> result) {
                log.info("Sent event {} with offset {}", event.getEvent(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send event {} due to {}", event.getEvent(), ex.getMessage());
            }
        });
    }
}
