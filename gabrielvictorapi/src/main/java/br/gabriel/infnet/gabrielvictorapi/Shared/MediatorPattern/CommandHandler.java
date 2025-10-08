package br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern;

public interface CommandHandler<TRequest extends Command<TResponse>, TResponse> {
    TResponse handle(TRequest request);
}
