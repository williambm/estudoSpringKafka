package com.wbm.springKafka.producer;

import com.wbm.springKafka.controller.CarDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarProducer {

    private final String topico;
    /**
     * Quando chamamos o KafkaTemplate passando como chave uma string, mas como valor um objeto temos que ter uma
     * classe de configuração para poder saber serializar ou deserializar esse objeto! Senão da (erro)!
     */
    @Autowired
    private final KafkaTemplate<String, CarDTO> kafkaTemplate;

    /**
     * Passo o @Value desse tópico no construtor para que o nome seja populado no momento de criação do objeto, porém
     * acredito que eu poderia externar essas configurações do tópico em outra classe de config.
     * @param topico
     * @param kafkaTemplate
     */
    public CarProducer( @Value(value = "${topic.name}") String topico, KafkaTemplate<String, CarDTO> kafkaTemplate) {
        this.topico = topico;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send (CarDTO carDTO){
        kafkaTemplate.send(topico,carDTO).addCallback(
                sucesso -> log.info("Message send " + sucesso.getProducerRecord().value()),
                falha -> log.error("mensagem falhou" + falha.getMessage())
        );
    }
}
