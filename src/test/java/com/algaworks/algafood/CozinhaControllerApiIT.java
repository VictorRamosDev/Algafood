package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
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

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CozinhaControllerApiIT {

    private static final int COZINHA_INEXISTENTE_ID = 1000;
    private int quantidadeCozinhasCadastradas;
    private Cozinha cozinhaAmericana;
    private String cozinhaJson;
    private String[] cozinhaNames;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

//    @Autowired
//    private Flyway flyway;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

//        flyway.migrate();    // Deve estar configurado no properties o caminho do "afterMigrate" para executar corretamente.

        databaseCleaner.clearTables();
        prepareData();

        cozinhaJson = ResourceUtils.getContentFromResource("/json/correto/cozinha-russa.json");
    }

    @Test
    void shouldRetornarStatus200_whenConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldConterNumeroDeCozinhasPreRegistradas_whenConsultarCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("", Matchers.hasSize(quantidadeCozinhasCadastradas))
            .body("nome", Matchers.hasItems(cozinhaNames));
    }

    @Test
    void shouldRetornarStatus201_WhenCadastrarCozinha() {
        given()
                .body(cozinhaJson)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldRetornarStatusERespostaCorretos_WhenConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", cozinhaAmericana.getId())
                .accept(ContentType.JSON)
        .when()
                .get("/{cozinhaId}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));
    }

    @Test
    void shouldRetornarStatus404_WhenConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_INEXISTENTE_ID)
                .accept(ContentType.JSON)
        .when()
                .get("/{cozinhaId}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        List<Cozinha> cozinhaList = DataPreparation.prepareCozinhaData();
        cozinhaRepository.saveAll(cozinhaList);
        cozinhaAmericana = cozinhaList.stream()
                .filter(cozinha -> cozinha.getNome().equals("Americana"))
                .findFirst()
                .orElse(null);

        quantidadeCozinhasCadastradas = cozinhaList.size();
        cozinhaNames = cozinhaList.stream().map(Cozinha::getNome)
                .toArray(String[]::new);
    }

}
