package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problema {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private OffsetDateTime timestamp;
    private String userMessage;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {

        private String nome;
        private String userMessage;
    }
}
