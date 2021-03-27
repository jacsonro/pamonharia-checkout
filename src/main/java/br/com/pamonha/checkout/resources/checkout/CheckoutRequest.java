package br.com.pamonha.checkout.resources.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 6787834043762243098L;
	String firstName; 
	String lastName; 
	String username; 
	String email; 
	String address; 
	String address2; 
	String country; 
	String state; 
	String city; 
	String zip;
	Boolean sameAddress;
	Boolean saveInfo;
	String paymentType;
	String ccName; 
	String ccNumber; 
	String ccExpiration; 
	String ccCvv;
	Sale sale;
}
