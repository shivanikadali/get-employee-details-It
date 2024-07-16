package get_employee_details.get_employee_details;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GetEmployeeDetailsApplication.class)
public class GetEmployeeDetIntegrationTesting {

    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp() throws IOException {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        // wireMockServer.startRecording("http://localhost:9999");
    }

    @AfterAll
    public static void tearDown() {
        // wireMockServer.stopRecording();
        wireMockServer.stop();
    }

    @Test
    public void getAll() {

        RestAssured.given()
                .get("http://localhost:" + wireMockServer.port() + "/employee")
                .then()
                .assertThat()
                .statusCode(200)
                .body("firstName[0]", equalTo("Eva"));
    }

    @Test
    public void createEmployee() throws IOException, URISyntaxException {

        String expectedRequest = new String(Files.readAllBytes(Paths.get("src/test/resources/employees.json")));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(expectedRequest)
                .post("http://localhost:" + wireMockServer.port() + "/employee")
                .then()
                .assertThat()
                .statusCode(201)
                .body("firstName", equalTo("thanu"))
                .body("lastName", equalTo("safari"));

    }
}