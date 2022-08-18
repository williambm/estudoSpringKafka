package com.wbm.consumerKafka.consumer.consumer;

import com.wbm.consumerKafka.consumer.DTO.CarDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CarConsumer {

    @Value(value = "${topic.name}")
    private String topic;

    @Value(value = "${spring.kafka.group-id}")
    private String groupID;

    /**
     * Usamos aqui a anotação do Spring Kafka KafkaListener que nela passamos alguns dados como o tópico, consumer group
     * , qual é o containerfactory que configuramos.. passamos esse cara como uma String
     *
     * Usamos tbm no listener um registro do tipo ConsumerRecord que nos tras várias informação de consumo como partição,
     * valor, offset, header etc ..
     * @param record
     */
    @KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.group-id}", containerFactory = "carKafkaListenerContainerFactory")
    public void listenTopicCar(ConsumerRecord<String, CarDTO> record){
        log.info("Recebeu a mensagem na partição: "+record.partition());
        log.info("Recebeu a mensagem: "+record.value());

    }
}
