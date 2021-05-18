package mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 @RestController
 public class ProductController {
        @Autowired
        ProductRepository productRepository;

        @RequestMapping(value = "/products/chkAndModifyStock",
                method = RequestMethod.GET,
                produces = "application/json;charset=UTF-8")

        public boolean chkAndModifyStock(HttpServletRequest request, HttpServletResponse response)
                throws Exception {
                boolean status = false;
                Long productId = Long.valueOf(request.getParameter("productId"));
                int qty = Integer.valueOf(request.getParameter("qty"));
                System.out.println("CHECK HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(productId);
                Product product = productRepository.findByProductId(productId);
                
                if(product.getStock() >= qty) {
                        status = true;
                        product.setStock(product.getStock() - qty);
                        productRepository.save(product);
                }

                return status;
        }
 }
