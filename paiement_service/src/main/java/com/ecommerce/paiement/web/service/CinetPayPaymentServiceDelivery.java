package com.ecommerce.paiement.web.service;


import com.ecommerce.paiement.web.dto.CinetPayPaymentDto;
import com.ecommerce.paiement.web.dto.UserDto;
import com.ecommerce.paiement.web.dto.OrderDto;
import org.springframework.stereotype.Service;

@Service
public class CinetPayPaymentServiceDelivery {

    private final UserService userService;
    private final OrderService orderService;

    public CinetPayPaymentServiceDelivery(UserService userService, OrderService orderService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    //@Retry(name = "paymentService")
    //@TimeLimiter(name = "paymentService")
    public CinetPayPaymentDto getDataFromOrderUser(Integer userId, int orderId) {

        OrderDto order = orderService.getData(orderId);
        UserDto user = userService.getData(userId);

        CinetPayPaymentDto cinetPayPaymentDto = new CinetPayPaymentDto();

        cinetPayPaymentDto.setTransactionId(Integer.toString(order.getidCommande()));
        int amount = (int)(Math.ceil(order.getMontant_livraison() / 5) * 5);
        //cinetPayPaymentDto.setAmount((int)order.getMontant_livraison());
        cinetPayPaymentDto.setAmount(amount);
        cinetPayPaymentDto.setCustomerId(Integer.toString(userId));
        cinetPayPaymentDto.setCustomerName(user.getNom());
        cinetPayPaymentDto.setCustomerSurname(user.getPrenom());
        cinetPayPaymentDto.setCustomerPhoneNumber(Integer.toString(user.getNumerotelephone()));
        cinetPayPaymentDto.setCustomerEmail(user.getEmail());
        String address = user.getVille() + "," + user.getQuartier();
        cinetPayPaymentDto.setCustomerAddress(address);
        cinetPayPaymentDto.setCustomerCity(user.getVille());

        return cinetPayPaymentDto;

    }

}
