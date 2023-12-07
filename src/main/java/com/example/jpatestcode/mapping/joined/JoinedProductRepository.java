package com.example.jpatestcode.mapping.joined;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JoinedProductRepository extends JpaRepository<JoinedProduct, Long> {

    List<JoinedProduct> findByDtype(String type);

}
