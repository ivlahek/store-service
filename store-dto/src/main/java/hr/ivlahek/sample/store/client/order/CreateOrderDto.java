package hr.ivlahek.sample.store.client.order;

import hr.ivlahek.sample.store.client.docs.DocumentationConstants;
import hr.ivlahek.sample.store.client.validation.ValidationMessages;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class CreateOrderDto {
    @NotNull(message = ValidationMessages.ORDER_EMAIL_IS_NULL)
    @NotEmpty(message = ValidationMessages.ORDER_EMAIL_IS_EMPTY)
    @Email(message = ValidationMessages.ORDER_NOT_A_VALID_EMAIL)
    @ApiModelProperty(value = DocumentationConstants.ORDER_EMAIL, required = true)
    private String email;

    @NotEmpty(message = ValidationMessages.ORDER_ITEM_LIST_EMPTY)
    @NotNull(message = ValidationMessages.ORDER_ITEM_LIST_NULL)
    @ApiModelProperty(value = DocumentationConstants.ORDER_ITEMS, required = true)
    @Valid
    private List<CreateOrderItemDto> orderItemDtos = new ArrayList<>();
}
