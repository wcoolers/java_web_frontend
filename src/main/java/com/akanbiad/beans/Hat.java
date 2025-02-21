package com.akanbiad.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Hat {
	
	private Long id;
	
	private String name;
	private double price;
	private int quantity;
	private String employeeName;
	private Lawyer lawyer;

}