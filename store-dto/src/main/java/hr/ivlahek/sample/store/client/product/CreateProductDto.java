package hr.ivlahek.sample.store.client.product;

import hr.ivlahek.sample.store.client.docs.DocumentationConstants;
import hr.ivlahek.sample.store.client.validation.ValidationMessages;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CreateProductDto {

    @NotNull(message = ValidationMessages.PRODUCT_NAME_NULL)
    @NotEmpty(message = ValidationMessages.PRODUCT_NAME_EMPTY)
    @ApiModelProperty(value = DocumentationConstants.PRODUCT_NAME, required = true)
    private String name;

    @NotNull(message = ValidationMessages.PRODUCT_SKU_NULL)
    @NotEmpty(message = ValidationMessages.PRODUCT_SKU_EMPTY)
    @ApiModelProperty(value = DocumentationConstants.PRODUCT_SKU, required = true)
    private String sku;

    @NotNull(message = ValidationMessages.PRODUCT_PRICE_NULL)
    @Positive(message = ValidationMessages.PRODUCT_PRICE_NEGATIVE_OR_ZERO)
    @ApiModelProperty(value = DocumentationConstants.PRODUCT_PRICE, required = true)
    private BigDecimal price;
}
