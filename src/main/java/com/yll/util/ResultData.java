package com.yll.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String result;
	private String userCode;
	private String delResult;
}