package com.prodemy.miniprojectbackend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
	private String title;
	private String image;
	private Long price;
	private Long categoryId;
}
