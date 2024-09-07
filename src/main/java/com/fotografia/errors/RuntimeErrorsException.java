package com.fotografia.errors;

public class RuntimeErrorsException extends RuntimeException{

    public RuntimeErrorsException(Error er) {
        super(er);

}
}
