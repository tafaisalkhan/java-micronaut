package com.micronet.controller;

import com.micronet.entity.Product;
import com.micronet.service.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.List;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Get
    public List<Product> list() {
        return productService.list();
    }

    @Get("/{id}")
    public HttpResponse<Product> get(Long id) {
        return productService.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Post
    public HttpResponse<Product> create(@Body Product product) {
        return HttpResponse.created(productService.create(product));
    }

    @Put("/{id}")
    public HttpResponse<Product> update(Long id, @Body Product product) {
        return HttpResponse.ok(productService.update(id, product));
    }

    @Delete("/{id}")
    public HttpResponse<Void> delete(Long id) {
        productService.delete(id);
        return HttpResponse.noContent();
    }

    @Get("/search")
    public List<Product> search(@QueryValue String name) {
        return productService.searchByName(name);
    }
}
