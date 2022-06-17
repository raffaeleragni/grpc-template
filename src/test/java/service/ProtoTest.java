package service;

import com.github.raffaeleragni.jx.injection.Injection;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ServiceGrpc.ServiceBlockingStub;
import service.ServiceOuterClass.Request;
import service.grpc.Service;

class ProtoTest {
  static Application app;
  static Channel channel;

  @BeforeAll
  static void serverStart() throws IOException {
    var session = new Injection();
    Service.register(session);
    app = session.createNew(Application.class);

    channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();

    app.start();
  }

  @AfterAll
  static void teardown() {
    app.stop();
  }

  ServiceBlockingStub client;

  @BeforeEach
  void setup() {
    client = ServiceGrpc.newBlockingStub(channel);
  }

  @Test
  void testTestProto() {
    var request = Request.getDefaultInstance();
    var response = client.call(request);

    assertThat(response, is(not(nullValue())));
  }
}
