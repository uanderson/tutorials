package es.usoar.tutorials.websocket;

import io.undertow.Undertow;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.websocket;
import static org.awaitility.Awaitility.await;

public class EchoTest {

  static int undertowPort;
  static Undertow undertow;

  @BeforeAll
  static void prepare() throws Exception {
    var serverSocket = new ServerSocket(0);
    undertowPort = serverSocket.getLocalPort();
    serverSocket.close();

    undertow = Undertow.builder()
      .addHttpListener(undertowPort, "localhost")
      .setHandler(path()
        .addPrefixPath("/echo", websocket((exchange, channel) -> {
          channel.getReceiveSetter().set(new AbstractReceiveListener() {
            @Override
            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
              WebSockets.sendText(message.getData(), channel, null);
            }
          });

          channel.resumeReceives();
        })))
      .build();

    undertow.start();
  }

  @AfterAll
  static void release() {
    undertow.stop();
  }

  @Test
  @DisplayName("Should echo the message")
  void echoTheMessage() throws Exception {
    var echoed = new AtomicBoolean(false);
    var webSocket = HttpClient.newHttpClient().newWebSocketBuilder()
      .buildAsync(URI.create("ws://localhost:" + undertowPort + "/echo"), new WebSocket.Listener() {
        @Override
        public CompletionStage<Void> onText(WebSocket webSocket, CharSequence data, boolean last) {
          webSocket.request(1);
          return CompletableFuture.completedFuture(data)
            .thenAccept(o -> echoed.set("Spread love. With a wonderful message.".contentEquals(o)));
        }
      })
      .get();

    webSocket.sendText("Spread love. ", false);
    webSocket.sendText("With a wonderful message.", true);
    await().untilTrue(echoed);
  }
}
