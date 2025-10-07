package com.store.domain.Shared.MediatorPattern;

public interface CommandHandler<TRequest extends Command<TResponse>, TResponse> {
    TResponse handle(TRequest request);
}
