package com.simol.appling.product.service;

import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.repo.ProductRepository;
import com.simol.appling.product.domain.vo.PostProductResponse;
import com.simol.appling.product.domain.vo.PutProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Transactional
    @Override
    public PostProductResponse createProduct(PostProductRequest postProductRequest) {
        ProductEntity saveProduct = productRepository.save(postProductRequest.toProductEntity());
        return PostProductResponse.createFrom(saveProduct);
    }

    @Transactional
    @Override
    public PutProductResponse putProduct(PutProductRequest putProductRequest) {
        ProductEntity productEntity = productRepository.findById(putProductRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상품입니다."));

        productEntity.update(putProductRequest);
        ProductEntity updateProductEntity = productRepository.save(productEntity);
        return PutProductResponse.from(updateProductEntity);
    }

}
