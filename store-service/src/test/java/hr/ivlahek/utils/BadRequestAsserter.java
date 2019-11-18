package hr.ivlahek.utils;

import hr.ivlahek.sample.store.client.error.ErrorMessage;
import hr.ivlahek.sample.store.exception.messages.ExceptionLogable;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class BadRequestAsserter {
    String endPoint;
    TestRestTemplate restTemplate;
    ErrorMessage genericErrorMesssage;
    private int httpCode;

    public BadRequestAsserter(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BadRequestAsserter executeGet(int id) {
        ResponseEntity<ErrorMessage> exchange = this.restTemplate.exchange(this.endPoint, HttpMethod.GET, (HttpEntity) null, ErrorMessage.class, new Object[]{id});
        this.genericErrorMesssage = exchange.getBody();
        this.httpCode = exchange.getStatusCodeValue();
        return this;
    }

    public BadRequestAsserter executePost(Object request, Object... args) {
        ResponseEntity<ErrorMessage> exchange = this.restTemplate.postForEntity(this.endPoint, request, ErrorMessage.class, args);
        this.genericErrorMesssage = exchange.getBody();
        this.httpCode = exchange.getStatusCodeValue();
        return this;
    }

    public BadRequestAsserter executePost(Object request) {
        ResponseEntity<ErrorMessage> exchange = this.restTemplate.postForEntity(this.endPoint, request, ErrorMessage.class, new Object[0]);
        this.genericErrorMesssage = exchange.getBody();
        this.httpCode = exchange.getStatusCodeValue();
        return this;
    }

    public BadRequestAsserter executePut(Object request, long id) {
        ResponseEntity<ErrorMessage> exchange = this.restTemplate.exchange(this.endPoint, HttpMethod.PUT, new HttpEntity<>(request), ErrorMessage.class, id);
        this.genericErrorMesssage = exchange.getBody();
        this.httpCode = exchange.getStatusCodeValue();
        return this;
    }

    public BadRequestAsserter executePostWithArgs(Object... args) {
        ResponseEntity<ErrorMessage> errorMessageResponseEntity = this.restTemplate.postForEntity(this.endPoint, (Object) null, ErrorMessage.class, args);
        this.genericErrorMesssage = errorMessageResponseEntity.getBody();
        this.httpCode = errorMessageResponseEntity.getStatusCodeValue();
        return this;
    }

    public void assertWithMessage(ExceptionLogable exceptionMessage) {
        String receivedExceptionMessage = this.genericErrorMesssage.getMessages().get(0);
        Assertions.assertThat(receivedExceptionMessage).isEqualTo(exceptionMessage.getMessage());
    }

    public void assertWithCode(ExceptionLogable exceptionMessage) {
        Assertions.assertThat(this.genericErrorMesssage.getCode()).isEqualTo(exceptionMessage.getErrorCode());
    }

    public void assertWithMessage(String... expectedMessage) {
        Assertions.assertThat(this.genericErrorMesssage.getMessages()).contains(expectedMessage);
    }

    public BadRequestAsserter withEndPoint(final String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public BadRequestAsserter assertBadRequest() {
        Assertions.assertThat(this.httpCode).isEqualTo(400);
        return this;
    }

    public BadRequestAsserter assertConflict() {
        Assertions.assertThat(this.httpCode).isEqualTo(409);
        return this;
    }

    public BadRequestAsserter assertInternalServerError() {
        Assertions.assertThat(this.httpCode).isEqualTo(500);
        return this;
    }

    public BadRequestAsserter assertNotFound() {
        Assertions.assertThat(this.httpCode).isEqualTo(404);
        return this;
    }
}
