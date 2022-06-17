package service.grpc;

import com.github.raffaeleragni.jx.injection.Injection;
import io.grpc.BindableService;

public interface Service extends BindableService {
  static void register(Injection injection) {
    injection.addImplementation(Service.class, ServiceImpl.class);
  }
}
