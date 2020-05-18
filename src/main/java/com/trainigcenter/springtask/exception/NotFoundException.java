package com.trainigcenter.springtask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
	
	private String name;

}
