package org.ccs.generator.exceptions;

import lombok.Data;

@Data
public class FailedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String resultCode = "100001";

    public FailedException(String errorMsg) {
        super(errorMsg);
    }

    public FailedException(String resultCode, String errorMsg) {
        super(errorMsg);
        this.resultCode = resultCode;
    }
}
