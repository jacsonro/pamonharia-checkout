package br.com.pamonha.checkout.service;

import br.com.pamonha.checkout.entity.CheckoutEntity;
import br.com.pamonha.checkout.resources.checkout.CheckoutRequest;

import java.util.Optional;


public interface CheckoutService {
    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);
    Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);
}
