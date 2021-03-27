package br.com.pamonha.checkout.resources.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class Sale {
	private Integer[] productQuantity;
	private Integer[] productId;
	double total;
}
