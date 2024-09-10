package com.simol.appling.product.service;

import com.simol.appling.product.domain.dto.GetProductListRequest;
import com.simol.appling.product.domain.dto.PostProductRequest;
import com.simol.appling.product.domain.dto.PutProductRequest;
import com.simol.appling.product.domain.entity.ProductEntity;
import com.simol.appling.product.domain.entity.ProductOptionEntity;
import com.simol.appling.product.domain.repo.ProductCustomRepository;
import com.simol.appling.product.domain.repo.ProductOptionRepository;
import com.simol.appling.product.domain.repo.ProductRepository;
import com.simol.appling.product.domain.vo.PostProductResponse;
import com.simol.appling.product.domain.vo.ProductListResponse;
import com.simol.appling.product.domain.vo.ProductVo;
import com.simol.appling.product.domain.vo.PutProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;
    private final ProductOptionRepository productOptionRepository;
    @Transactional
    @Override
    public PostProductResponse createProduct(PostProductRequest postProductRequest) {
        ProductEntity saveProduct = productRepository.save(ProductEntity.from(postProductRequest));
        // todo saveProduct에 option list 추가하기
        List<ProductOptionEntity> productOptionList = postProductRequest.getProductOption().stream()
                .map(f -> ProductOptionEntity.from(f, saveProduct))
                .collect(Collectors.toList());
        productOptionRepository.saveAll(productOptionList);

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

    @Override
    public ProductListResponse getProductList(GetProductListRequest getProductListRequest) {
        Page<ProductEntity> productPage = productCustomRepository.findAll(getProductListRequest);

        int totalPage = productPage.getTotalPages();
        Long totalElements = productPage.getTotalElements();
        int numberOfElement = productPage.getNumberOfElements();
        Boolean last = productPage.isLast();
        Boolean empty = productPage.isEmpty();

        List<ProductVo> productVoList = productPage.stream()
                .map(ProductVo::from)
                .toList();

        return ProductListResponse.builder()
                .productList(productVoList)
                .totalPage(totalPage)
                .totalElements(totalElements)
                .numberOfElement(numberOfElement)
                .last(last)
                .empty(empty)
                .build();
    }

}
