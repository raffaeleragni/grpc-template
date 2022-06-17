package service;

import com.github.raffaeleragni.jx.injection.Injection;
import service.grpc.Service;

class Main {
  public static void main(String[] args) throws Exception {
    var session = new Injection();
    Service.register(session);
    session.createNew(Application.class).start();
  }
}
