package br.com.pamonha.checkout.service;

import br.com.pamonha.checkout.entity.CheckoutEntity;
import br.com.pamonha.checkout.entity.CheckoutItemEntity;
import br.com.pamonha.checkout.entity.ShippingEntity;
import br.com.pamonha.checkout.event.CheckoutCreatedEvent;
import br.com.pamonha.checkout.repository.CheckoutRepository;
import br.com.pamonha.checkout.resources.checkout.CheckoutRequest;
import br.com.pamonha.checkout.streaming.CheckoutCreatedSource;
import br.com.pamonha.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {
    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;


    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        CheckoutEntity entity = null;
        try {
            final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                    .code(UUID.randomUUID().toString())
                    .status(CheckoutEntity.Status.CREATED)
                    .saveInformation(checkoutRequest.getSaveInfo())
                    .saveAddress(checkoutRequest.getSameAddress())
                    .shipping(ShippingEntity.builder()
                            .address(checkoutRequest.getAddress())
                            .addressTwo(checkoutRequest.getAddress2())
                            .country(checkoutRequest.getCountry())
                            .state(checkoutRequest.getState())
                            .city(checkoutRequest.getCity())
                            .cep(checkoutRequest.getZip())
                            .build())
                    .build();
            Integer[] products = checkoutRequest.getSale().getProductId();
            Integer[] productsQuantity = checkoutRequest.getSale().getProductQuantity();
            List<CheckoutItemEntity> items = new ArrayList<>();

            for(int index = 0; index < products.length; index++){
                CheckoutItemEntity  item = new CheckoutItemEntity();
                item.setCheckout(checkoutEntity);
                item.setProductId(products[index]);
                item.setProductQuantity(productsQuantity[index]);
                items.add(item);
            }
            checkoutEntity.setItems(items.stream()
                    .map(product -> CheckoutItemEntity.builder()
                            .productId(product.getProductId())
                            .productQuantity(product.getProductQuantity())
                            .checkout(product.getCheckout())
                            .build())
                    .collect(Collectors.toList()));


            entity = checkoutRepository.save(checkoutEntity);

            final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                    .setCheckoutCode(entity.getCode())
                    .setStatus(entity.getStatus().name())
                    .build();

            log.info("Event_21={}", entity.getCode());
            final MessageBuilder<CheckoutCreatedEvent> paymentCreatedEventMessageBuilder = MessageBuilder.withPayload(checkoutCreatedEvent);
            final Message<CheckoutCreatedEvent> build = paymentCreatedEventMessageBuilder.build();
            log.info("Event_Build_Headers={}", build.getHeaders().toString());
            log.info("Event_Build={}", build.getPayload().toString());
            checkoutCreatedSource
                    .output()
                    .send(build);
        } catch (Exception e) {
            log.error("Couldn't close the reader!", e);
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}
