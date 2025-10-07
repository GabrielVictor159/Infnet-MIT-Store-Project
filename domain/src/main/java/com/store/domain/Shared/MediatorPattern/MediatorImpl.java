package com.store.domain.Shared.MediatorPattern;

import com.store.domain.Shared.Exceptions.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MediatorImpl implements Mediator, InitializingBean {

   private final ApplicationContext applicationContext;
    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> handlers = new HashMap<>();
    private final Validator validator;

    public MediatorImpl(ApplicationContext applicationContext, Validator validator) {
        this.applicationContext = applicationContext;
        this.validator = validator;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TResponse> TResponse Handler(Command<TResponse> command) {
        
        Set<ConstraintViolation<Command<TResponse>>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            
            throw new BadRequestException(errorMessages);
        }
        CommandHandler<Command<TResponse>, TResponse> handler = (CommandHandler<Command<TResponse>, TResponse>) handlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("Nenhum handler encontrado para a request: " + command.getClass().getName());
        }
        return handler.handle(command);
    }
    
    @Override
    public void afterPropertiesSet() {
        Map<String, CommandHandler> beans = applicationContext.getBeansOfType(CommandHandler.class);

        for (CommandHandler<?, ?> handler : beans.values()) {
            Class<?> requestType = GenericTypeResolver.resolveTypeArguments(handler.getClass(), CommandHandler.class)[0];
            handlers.put((Class<? extends Command<?>>) requestType, handler);
        }
    }
}
