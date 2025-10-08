package br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern;

public interface Mediator {
    <TResponse> TResponse Handler(Command<TResponse> command);
}
