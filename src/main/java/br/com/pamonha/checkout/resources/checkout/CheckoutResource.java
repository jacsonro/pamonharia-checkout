package br.com.pamonha.checkout.resources.checkout;

import br.com.pamonha.checkout.entity.CheckoutEntity;
import br.com.pamonha.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/checkout")
@RequiredArgsConstructor
public class CheckoutResource{
	private final CheckoutService checkoutService;

	@PostMapping("/")
	public ResponseEntity<CheckoutResponse> create(@RequestBody CheckoutRequest checkoutRequest){
		final CheckoutEntity checkoutEntity = checkoutService.create(checkoutRequest).orElseThrow();
		final CheckoutResponse checkoutResponse = CheckoutResponse.builder()
		.code(checkoutEntity.getCode())
		.build();
		return ResponseEntity.status(HttpStatus.CREATED).body(checkoutResponse);
	}
}
