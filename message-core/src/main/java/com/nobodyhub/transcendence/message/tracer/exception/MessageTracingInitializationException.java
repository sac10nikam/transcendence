package com.nobodyhub.transcendence.message.tracer.exception;

public class MessageTracingInitializationException extends RuntimeException {
    public MessageTracingInitializationException(Throwable cause) {
        super("Fail to initialize the context for message tracing.", cause);
    }

    public MessageTracingInitializationException(String message) {
        super(message);
    }
}
