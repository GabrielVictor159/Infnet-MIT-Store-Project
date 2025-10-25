package br.gabriel.infnet.gabrielvictorapi.Application.Handlers.Product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import br.gabriel.infnet.gabrielvictorapi.Application.Commands.Product.SearchProductCommand;
import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Product.ProductDTO;
import br.gabriel.infnet.gabrielvictorapi.Domain.Models.Product;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Repositories.ProductRepository;
import br.gabriel.infnet.gabrielvictorapi.Infraestructure.Specifications.ProductSpecification;
import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.CommandHandler;
import jakarta.transaction.Transactional;

@Component
public class SearchProductHandler implements CommandHandler<SearchProductCommand, List<ProductDTO>> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public List<ProductDTO> handle(SearchProductCommand command) {
        Specification<Product> spec = ProductSpecification.findByCriteria(
            command.getId(),
            command.getName()
        );

        Pageable pageable = PageRequest.of(
            command.getPage(),
            command.getSize().getValue()
        );
        var products = productRepository.findAll(spec, pageable);

        List<ProductDTO> result = new ArrayList<ProductDTO>();

        for (Product product : products) {
            var itemResult = new ProductDTO();
            BeanUtils.copyProperties(product, itemResult);
            result.add(itemResult);
        }

        return result;
    }
}
