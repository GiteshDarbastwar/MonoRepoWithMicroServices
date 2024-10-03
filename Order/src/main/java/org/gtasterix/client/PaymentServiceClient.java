package org.gtasterix.client;

import org.gtasterix.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping(value = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    String initiatePayment(@RequestBody PaymentRequest paymentRequest);
}