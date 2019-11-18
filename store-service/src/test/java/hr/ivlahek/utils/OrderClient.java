package hr.ivlahek.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ivlahek.sample.store.client.order.OrderResourceEndpoints;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class OrderClient {
    private TestRestTemplate testRestTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public OrderClient(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public List<OrderDto> getPaged(int page, int size, String dateFrom, String dateTo) {
        List pageResponse = testRestTemplate.getForEntity(buildForUrl(OrderResourceEndpoints.ORDERS, page, size, dateFrom, dateTo), PageResponseDto.class).getBody().getContent();

        List<OrderDto> orderDtos = new ArrayList<>();
        pageResponse.forEach(o -> orderDtos.add(objectMapper.convertValue(o, OrderDto.class)));
        return orderDtos;
    }


    public static String buildForUrl(String path, int page, int pageSize, String dateFrom, String dateTo) {
        return UriComponentsBuilder.fromPath(path)
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .queryParam("page", page)
                .queryParam("size", pageSize).build().toString();
    }
}
