package com.xxx.stat.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * 统一内部异常
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StatException extends RuntimeException {
    public StatException(String message) {
        super(message);
    }

    @JsonIgnore
    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @JsonIgnore
    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @JsonIgnore
    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }
}
