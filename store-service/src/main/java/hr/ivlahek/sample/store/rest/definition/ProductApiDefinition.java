package hr.ivlahek.sample.store.rest.definition;

import hr.ivlahek.sample.store.client.error.ErrorMessage;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.client.product.CreateProductDto;
import hr.ivlahek.sample.store.client.product.ProductDto;
import hr.ivlahek.sample.store.client.product.ProductResourceEndpoints;
import hr.ivlahek.sample.store.config.ApiPageable;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ProductApiDefinition {
    @PostMapping(path = ProductResourceEndpoints.PRODUCTS)
    @ApiOperation("Create product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a product.", response = ProductDto.class),
            @ApiResponse(code = 409, message = "Product with the provided name already exists! Error code 1", response = ErrorMessage.class),
    })
    ProductDto createProduct(@Valid @RequestBody @NotNull CreateProductDto createProductDto);


    @PutMapping(path = ProductResourceEndpoints.PRODUCTS_BY_ID)
    @ApiOperation("Update product.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated a product.", response = ProductDto.class),
            @ApiResponse(code = 404, message = "Product does not exist! Error code 2", response = ErrorMessage.class),
            @ApiResponse(code = 409, message = "Product with the provided name already exists! Error code 1", response = ErrorMessage.class),
    })
    ProductDto updateProduct(
            @PathVariable("productId") long productId,
            @Valid @RequestBody @NotNull CreateProductDto createProductDto);


    @GetMapping(path = ProductResourceEndpoints.PRODUCTS)
    @ApiOperation("Get all products. Paging supported.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched a list of products.", response = PageResponseDto.class),
    })
    @ApiPageable
    PageResponseDto<ProductDto> getAllProducts(
            @ApiParam("Paging parameter")
            @NotNull Pageable pageable);

    @DeleteMapping(path = ProductResourceEndpoints.PRODUCTS_BY_ID)
    @ApiOperation("Delete product identified with id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted a product.", response = Void.class),
    })
    void deleteProduct(
            @PathVariable("productId") long productId);
}
