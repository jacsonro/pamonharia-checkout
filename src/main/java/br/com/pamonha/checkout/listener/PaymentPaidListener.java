package br.com.pamonha.checkout.listener;

import br.com.pamonha.checkout.entity.CheckoutEntity;
import br.com.pamonha.checkout.service.CheckoutService;
import br.com.pamonha.checkout.streaming.PaymentPaidSink;
import br.com.pamonha.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentPaidListener {
    private final CheckoutService checkoutService;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent paymentCreatedEvent) {
        checkoutService.updateStatus(paymentCreatedEvent.getCheckoutCode().toString(), CheckoutEntity.Status.APPROVED);
    }
}