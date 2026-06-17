package com.micronet.repository;

import com.micronet.entity.Product;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.H2)
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameContaining(String name);
}
