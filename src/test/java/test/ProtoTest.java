package test;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.ProtoSample.SearchRequest;
import io.grpc.examples.SearchServiceGrpc;
import io.grpc.examples.SearchServiceGrpc.SearchServiceBlockingStub;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProtoTest {
  static Server server;
  static Channel channel;

  @BeforeAll
  static void serverStart() throws IOException {
    server = ServerBuilder.forPort(8080).addService(new SampleService()).build();
    channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();

    server.start();
  }

  @AfterAll
  static void teardown() {
    server.shutdownNow();
  }

  SearchServiceBlockingStub client;

  @BeforeEach
  void setup() {
    client = SearchServiceGrpc.newBlockingStub(channel);
  }

  @Test
  void testTestProto() {
    var request = SearchRequest.newBuilder()
      .setQuery("query")
      .setResultPerPage(1)
      .setPageNumber(0)
      .build();
    var response = client.search(request);

    assertThat(response.getResults(0).getTitle(), is("title"));
    assertThat(response.getResults(0).getUrl(), is("url"));
  }
}
