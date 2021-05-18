package mall;

import mall.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired
    ProductRepository productrepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_ModifyStock(@Payload OrderCancelled orderCancelled){

        if(orderCancelled.isMe()){
            System.out.println("##### listener ModifyStock : " + orderCancelled.toJson());
            Product product = productrepository.findByProductId(Long.valueOf(orderCancelled.getProductId()));
            product.setStock(product.getStock()+orderCancelled.getQty());
            productrepository.save(product);
        }
    }

}
