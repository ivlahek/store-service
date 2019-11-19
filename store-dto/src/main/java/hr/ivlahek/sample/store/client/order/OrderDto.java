package hr.ivlahek.sample.store.client.order;

import hr.ivlahek.sample.store.client.docs.DocumentationConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {
    @ApiModelProperty(value = DocumentationConstants.PLACED_ORDER_ID, required = true)
    private Long id;

    @ApiModelProperty(value = DocumentationConstants.PLACED_ORDER_EMAIL, required = true)
    private String email;

    @ApiModelProperty(value = DocumentationConstants.PLACED_ORDER_TOTAL_PRICE, required = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(value = DocumentationConstants.PLACED_ORDER_DATE_CREATED, required = true)
    private Date dateCreated;

    @ApiModelProperty(value = DocumentationConstants.PLACED_ORDER_ITEMS, required = true)
    private List<OrderItemDto> orderItemDtos = new ArrayList<>();

}
