package com.prodemy.miniprojectbackend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResponse<D> {
	private int status;
	private String message;
	private D data;
}
