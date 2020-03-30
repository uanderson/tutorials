package es.usoar.tutorials.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

class WiremockTest {

  static WireMockServer wireMockServer;
  static HttpClient httpClient;

  @BeforeAll
  static void prepare() throws Exception {
    wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
    httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_2)
      .build();

    wireMockServer.start();
  }

  @AfterAll
  static void release() {
    wireMockServer.stop();
  }

  @Test
  @DisplayName("Should send basic request")
  void sendBasicRequest() throws IOException, InterruptedException {
    HttpRequest httpRequest = HttpRequest.newBuilder()
      .uri(URI.create("http://localhost:" + wireMockServer.port() + "/actions"))
      .header("content-type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString("{\"key\":\"Value\"}"))
      .build();

    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    wireMockServer.verify(postRequestedFor(urlEqualTo("/actions"))
      .withHeader("content-type", equalTo("application/json"))
      .withRequestBody(matchingJsonPath("$.key", equalTo("Value"))));
  }
}
