package hr.ivlahek.sample.store.rest.definition;

import hr.ivlahek.sample.store.client.error.ErrorMessage;
import hr.ivlahek.sample.store.client.order.CreateOrderDto;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderResourceEndpoints;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import hr.ivlahek.sample.store.client.validation.ValidationMessages;
import hr.ivlahek.sample.store.config.ApiPageable;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public interface OrderApiDefinition {

    @PostMapping(path = OrderResourceEndpoints.ORDERS)
    @ApiOperation("Post an order to the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully posted an order.", response = OrderDto.class),
            @ApiResponse(code = 500, message = "Provided products can not be found in the database. Error code 3.", response = ErrorMessage.class)
    })
    @ApiPageable
    OrderDto placeOrder(@Valid @RequestBody @NotNull CreateOrderDto createOrderDto);


    @GetMapping(path = OrderResourceEndpoints.ORDERS)
    @ApiOperation("Get information about orders placed within a given time period.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
    })
    @ApiPageable
    PageResponseDto<OrderDto> getOrders(
            @ApiParam("Paging parameter")
            @Valid @NotNull Pageable pageable,

            @RequestParam("dateFrom")
            @ApiParam("From period of time for which data will be returned")
            @NotNull(message = ValidationMessages.DATE_FROM_NOT_FOUND)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateFrom,

            @NotNull(message = ValidationMessages.DATE_TO_NOT_FOUND)
            @RequestParam("dateTo")
            @ApiParam("To period of time for which data will be returned")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateTo);


}
