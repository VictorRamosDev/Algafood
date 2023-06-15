package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String MSG_DADOS_INVALIDOS = "Um ou mais campos estão inválidos. " +
            "Faça o preenchimento correto e tente novamente.";
    public static final String MSG_PARAMETROS_INVALIDOS = "O parâmetro, de URL, '%s' recebeu o valor '%s', " +
            "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.";
    public static final String MSG_MENSAGEM_INCOPREENSIVEL = "O corpo da requisição está inválido. Verifique erro de sintaxe";
    public static final String MSG_CAMPO_INEXISTENTE = "O campo '%s' não existe. Remova-o da requisição.";
    public static final String MSG_TIPO_INVALIDO = "A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. " +
            "Corrija e informe um valor compatível com o tipo '%s.'";
    public static final String MSG_RECURSO_INEXISTENTE = "O recurso '%s', que você tentou acessar, é inexistente.";
    public static final String MSG_ERRO_INESPERADO_SISTEMA = "Ocorreu um erro interno inesperado no sistema. " +
            "Tente novamente e se o problema persistir, contacte o administrador do sistema.";

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format(MSG_RECURSO_INEXISTENTE, ex.getRequestURL());
        Problema problem = createProblemaBuilder(status, detail, problemType)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        BindingResult bindingResult = ex.getBindingResult();
        List<Problema.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> Problema.Field.builder()
                        .nome(fieldError.getField())
                        .userMessage(fieldError.getDefaultMessage())
                        .build()
                ).collect(Collectors.toList());

        Problema problem = createProblemaBuilder(status, MSG_DADOS_INVALIDOS, problemType)
                .userMessage(MSG_DADOS_INVALIDOS)
                .fields(problemFields)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        logger.error(ex.getMessage(), ex);
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String parameterName = ex.getParameter().getParameterName();

        String detail = String.format(MSG_PARAMETROS_INVALIDOS, parameterName, ex.getValue(),
                ex.getParameter().getParameterType().getSimpleName());
        Problema problem = createProblemaBuilder(status, detail, problemType).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof IgnoredPropertyException) {
            return handleIgnoredProperty((IgnoredPropertyException) rootCause, headers, status, request);
        } else if (rootCause instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedProperty((UnrecognizedPropertyException) rootCause, headers, status, request);
        }

        ProblemType problemType = ProblemType.MENSAGEM_IMCOPREENSIVEL;
        Problema problema = createProblemaBuilder(
                status, MSG_MENSAGEM_INCOPREENSIVEL, problemType
        ).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleUnrecognizedProperty(
            UnrecognizedPropertyException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ProblemType problemType = ProblemType.MENSAGEM_IMCOPREENSIVEL;
        String fieldName = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format(
                MSG_CAMPO_INEXISTENTE,
                fieldName);
        Problema problem = createProblemaBuilder(status, detail, problemType).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleIgnoredProperty(
            IgnoredPropertyException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ProblemType problemType = ProblemType.MENSAGEM_IMCOPREENSIVEL;
        String fieldName = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format(MSG_CAMPO_INEXISTENTE, fieldName);
        Problema problem = createProblemaBuilder(status, detail, problemType).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(
            InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        String fieldName = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));

        ProblemType problemType = ProblemType.MENSAGEM_IMCOPREENSIVEL;
        String detail = String.format(MSG_TIPO_INVALIDO,
                fieldName, ex.getValue(), ex.getTargetType().getSimpleName());
        Problema problem = createProblemaBuilder(status, detail, problemType).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllOthers(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String detail = MSG_ERRO_INESPERADO_SISTEMA;
        Problema problem = createProblemaBuilder(status, detail, problemType)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        Problema problema = createProblemaBuilder(status, ex.getMessage(), problemType).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        Problema problema = createProblemaBuilder(status, ex.getMessage(), problemType).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        Problema problema = createProblemaBuilder(status, ex.getMessage(), problemType).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        if (body == null) {
            body = Problema.builder()
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .detail(ex.getMessage())
                    .timestamp(LocalDateTime.now())
                    .userMessage(MSG_ERRO_INESPERADO_SISTEMA)
                    .build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .detail((String) body)
                    .timestamp(LocalDateTime.now())
                    .userMessage(MSG_ERRO_INESPERADO_SISTEMA)
                    .build();
        }
        logger.error(ex.getMessage(), ex);

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status, String detail, ProblemType problemType) {
        return Problema.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }

}
