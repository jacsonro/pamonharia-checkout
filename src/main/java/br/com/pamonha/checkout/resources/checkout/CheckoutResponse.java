package br.com.pamonha.checkout.resources.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponse implements Serializable{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String code;
}
