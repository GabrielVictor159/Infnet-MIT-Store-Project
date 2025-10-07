package com.store.domain.Shared.MediatorPattern;

public interface Mediator {
    <TResponse> TResponse Handler(Command<TResponse> command);
}
