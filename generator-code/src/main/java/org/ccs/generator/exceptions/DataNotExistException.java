package org.ccs.generator.exceptions;

public class DataNotExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String resultCode;

    public DataNotExistException(String resultCode, String errorMsg) {
        super(errorMsg);
        this.resultCode = resultCode;
    }
}
