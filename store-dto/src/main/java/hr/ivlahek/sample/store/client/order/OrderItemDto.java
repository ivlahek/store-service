package hr.ivlahek.sample.store.client.order;

import hr.ivlahek.sample.store.client.docs.DocumentationConstants;
import hr.ivlahek.sample.store.client.validation.ValidationMessages;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class OrderItemDto {
    @ApiModelProperty(value = DocumentationConstants.ORDER_ITEM_ID, required = true)
    @NotNull(message = ValidationMessages.PRODUCT_ID_NULL)
    private Long productId;

    @NotNull(message = ValidationMessages.ORDER_ITEM_QUANTITY_NULL)
    @Positive(message = ValidationMessages.ORDER_ITEM_QUANTITY_NEGATIVE_NUMBER)
    @ApiModelProperty(value = DocumentationConstants.ORDER_ITEM_QUANTITY, required = true)
    private Integer quantity;

    @ApiModelProperty(value = DocumentationConstants.ORDER_ITEM_PRICE, required = true)
    private BigDecimal orderItemPrice;

}
