package service;

import static com.github.raffaeleragni.jx.exceptions.Exceptions.unchecked;
import com.github.raffaeleragni.jx.injection.Injection;
import com.github.raffaeleragni.jx.jdbc.Jdbc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ServiceGrpc.ServiceBlockingStub;
import service.ServiceOuterClass.GetRequest;
import service.ServiceOuterClass.SetRequest;
import service.data.ServiceData;
import service.grpc.Service;

class ProtoTest {
  static Application app;
  static Channel channel;
  static Jdbc jdbc;

  @BeforeAll
  static void serverStart() throws IOException {
    jdbc = new Jdbc(() -> memoryDB("test"));

    var session = new Injection();
    session.addInstance(Jdbc.class, jdbc);
    Service.register(session);
    ServiceData.register(session);

    app = session.createNew(Application.class);

    channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();

    jdbc.execute("create table tablex(id int not null, v varchar(255) not null, primary key(id))", st-> {});
    jdbc.execute("insert into tablex(id, v) values (1, 'a')", st -> {});

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
    var request = GetRequest.newBuilder()
      .setId(1)
      .build();
    var response = client.get(request);

    assertThat(response.getValue(), is("a"));

    client.set(SetRequest.newBuilder()
      .setId(1)
      .setValue("b")
      .build());

    request = GetRequest.newBuilder()
      .setId(1)
      .build();
    response = client.get(request);

    assertThat(response.getValue(), is("b"));
  }

  static Connection memoryDB(String name) {
    return unchecked(() ->
      DriverManager.getConnection("jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1", "sa", "-")
    );
  }
}
