package br.com.pamonha.checkout.config;

import br.com.pamonha.checkout.streaming.CheckoutCreatedSource;
import br.com.pamonha.checkout.streaming.PaymentPaidSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {
        CheckoutCreatedSource.class,
        PaymentPaidSink.class
})
public class StreamingConfig {
}

