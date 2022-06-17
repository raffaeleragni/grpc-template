package service;

import static com.github.raffaeleragni.jx.exceptions.Exceptions.unchecked;
import com.github.raffaeleragni.jx.injection.Injection;
import com.github.raffaeleragni.jx.jdbc.Jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import service.data.ServiceData;
import service.grpc.Service;

class Main {
  public static void main(String[] args) throws Exception {
    var jdbc = new Jdbc(() -> memoryDB("service"));

    var session = new Injection();
    session.addInstance(Jdbc.class, jdbc);
    Service.register(session);
    ServiceData.register(session);

    var app = session.createNew(Application.class);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> app.stop()));
    app.start();
  }

  static Connection memoryDB(String name) {
    return unchecked(() ->
      DriverManager.getConnection("jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1", "sa", "-")
    );
  }
}
