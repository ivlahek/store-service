package hr.ivlahek.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ivlahek.sample.store.client.order.OrderDto;
import hr.ivlahek.sample.store.client.order.OrderResourceEndpoints;
import hr.ivlahek.sample.store.client.page.PageResponseDto;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class OrderClient {
    private TestRestTemplate testRestTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public OrderClient(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public static String buildForUrl(String path, int page, int pageSize, String dateFrom, String dateTo) {
        return UriComponentsBuilder.fromPath(path)
                .queryParam("dateFrom", dateFrom)
                .queryParam("dateTo", dateTo)
                .queryParam("page", page)
                .queryParam("size", pageSize).build().toString();
    }

    public List<OrderDto> getPaged(int page, int size, String dateFrom, String dateTo) {
        List pageResponse = testRestTemplate.getForEntity(buildForUrl(OrderResourceEndpoints.ORDERS, page, size, dateFrom, dateTo), PageResponseDto.class).getBody().getContent();
        return (List<OrderDto>) pageResponse.stream().map(o -> objectMapper.convertValue(o, OrderDto.class)).collect(Collectors.toList());
    }
}
