package service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import service.grpc.Service;

class Application {
  Server server;

  Application(Service service) {
    server = ServerBuilder.forPort(8080)
      .addService(service)
      .build();
  }

  void start() throws IOException, InterruptedException {
    server.start();
    server.awaitTermination();
  }

  void stop() {
    server.shutdownNow();
  }
}
