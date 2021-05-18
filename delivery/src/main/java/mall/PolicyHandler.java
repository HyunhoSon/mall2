package mall;

import mall.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrdered_PrepareShip(@Payload Ordered ordered){

        if(ordered.isMe()){
            // SNS Send.
            // CJ Payload.
            // Delivery Aggregate Save.
            Delivery delivery = new Delivery();
            delivery.setOrderId(ordered.getId());
            delivery.setStatus("DeliveryStarted");
            
            deliveryRepository.save(delivery);

        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_DeleteCancelledOrder(@Payload OrderCancelled orderCancelled){

        if(orderCancelled.isMe()){
            Delivery delivery = deliveryRepository.findByOrderId(orderCancelled.getId());
            deliveryRepository.delete(delivery);
        }
    }

}
