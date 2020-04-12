package hr.ivlahek.sample.store.client.order;

import hr.ivlahek.sample.store.client.docs.DocumentationConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDto {
    @ApiModelProperty(value = DocumentationConstants.ORDER_ID, required = true)
    private Long id;

    @ApiModelProperty(value = DocumentationConstants.ORDER_EMAIL, required = true)
    private String email;

    @ApiModelProperty(value = DocumentationConstants.ORDER_TOTAL_PRICE, required = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(value = DocumentationConstants.ORDER_DATE_CREATED, required = true)
    private Date dateCreated;

}
