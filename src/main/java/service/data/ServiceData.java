package service.data;

import com.github.raffaeleragni.jx.injection.Injection;

public interface ServiceData {
  record TableX(long id, String v) {}

  TableX get(long id);
  void set(long id, String v);

  static void register(Injection injection) {
    injection.addImplementation(ServiceData.class, ServiceDataImpl.class);
  }
}
