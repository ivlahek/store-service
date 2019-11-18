package hr.ivlahek.sample.store.client.product;

import hr.ivlahek.sample.store.client.docs.DocumentationConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    @ApiModelProperty(name = DocumentationConstants.PRODUCT_ID)
    private Long id;
    @ApiModelProperty(name = DocumentationConstants.PRODUCT_NAME)
    private String name;
    @ApiModelProperty(name = DocumentationConstants.PRODUCT_DESCRIPTION)
    private String description;
    @ApiModelProperty(name = DocumentationConstants.PRODUCT_PRICE)
    private double price;

}
