package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DataPreparation;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class RestauranteControllerApiIT {

    @LocalServerPort
    private int port;

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";
    private static final long RESTAURANTE_INEXISTENTE_ID = 100L;

    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;

    private int quantidadeRestaurantesCadastrados;
    private Restaurante restauranteMcDonalds;
    private String[] restauranteNames;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.basePath = "/restaurantes";
        RestAssured.port = port;

        databaseCleaner.clearTables();
        prepareData();

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource("/json/correto/restaurante-new-york-barbecue.json");
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-taxa-frete.json");
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-sem-cozinha.json");
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-new-york-barbecue-com-cozinha-inexistente.json");
    }

    // Teste de Listagem - Retornar status 200
    @Test
    void shouldRetornarStatus200_WhenListarRestaurantes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    // Teste de Listagem - Quantidade de restaurantes pré-cadastrados
    @Test
    void shouldRetornarQuantidadeCorreta_WhenConsultarRestaurantes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("", Matchers.hasSize(quantidadeRestaurantesCadastrados))
                .body("nome", Matchers.hasItems(restauranteNames));
    }

    // Teste de Busca - retornar status 200 e restaurante correto
    @Test
    void shouldReturnStatus200_whenBuscarRestauranteEspecifico() {
        given()
                .accept(ContentType.JSON)
                .pathParam("restauranteId", restauranteMcDonalds.getId())
        .when()
                .get("/{restauranteId}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo(restauranteMcDonalds.getNome()));
    }

    // Teste de Cadastro - retornar status 201
    @Test
    void shouldRetornar201_WhenSalvarRestaurante() {
        given()
                .body(jsonRestauranteCorreto)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    // Teste de 404 na busca
    @Test
    void shouldRetornarStatus404_whenBuscaRestauranteInexistente() {
        given()
                .accept(ContentType.JSON)
                .pathParam("restauranteId", RESTAURANTE_INEXISTENTE_ID)
        .when()
                .get("/{restauranteId}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
        given()
                .body(jsonRestauranteSemFrete)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    void shouldRetornarStatus400_whenCadastrarRestauranteSemCozinha() {
        given()
                .body(jsonRestauranteSemCozinha)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    void shouldRetornarStatus400_whenCadastrarRestauranteComCozinhaInexistente() {
        given()
                .body(jsonRestauranteComCozinhaInexistente)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }

    private void prepareData() {
        Set<FormaPagamento> formasPagamento = DataPreparation.prepareFormaPagamentoData();
        formaPagamentoRepository.saveAll(formasPagamento);

        List<Restaurante> restauranteList = DataPreparation.prepareRestauranteData(cozinhaRepository);
        restauranteList.forEach(restaurante -> restaurante.setFormasPagamento(formasPagamento));
        restauranteList = restauranteRepository.saveAll(restauranteList);

        quantidadeRestaurantesCadastrados = restauranteList.size();
        restauranteMcDonalds = restauranteList.stream()
                .filter(restaurante -> restaurante.getNome().equals("McDonalds"))
                .findFirst()
                .orElse(null);
        restauranteNames = restauranteList.stream().map(Restaurante::getNome).toArray(String[]::new);
    }

}
