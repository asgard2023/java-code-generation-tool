package org.ccs.generator.exceptions;

import lombok.Data;

@Data
public class DataNotExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String resultCode = "100003";

    public DataNotExistException(String errorMsg) {
        super(errorMsg);
    }

    public DataNotExistException(String resultCode, String errorMsg) {
        super(errorMsg);
        this.resultCode = resultCode;
    }
}
