package br.com.springkafka.producer;

import br.com.springkafka.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PeopleProducer {

    private final String topicName;

    //O People aqui é o da classe gerada pelo avro no java e não do entity !!!!
    private final KafkaTemplate<String, People> kafkaTemplate;

    public PeopleProducer(@Value("${topic.name}") String topicName, KafkaTemplate<String, People> kafkaTemplate) {
        this.topicName = topicName;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageByController(People people) {

        kafkaTemplate.send(topicName, people).addCallback(
                sucesso ->log.info("Mensagem enviada com Sucesso !"),
                falha ->log.error("Alguma coisa deu errado no envio !")
        );
    }
}
