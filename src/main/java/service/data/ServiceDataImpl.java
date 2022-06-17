package service.data;

import com.github.raffaeleragni.jx.jdbc.Jdbc;

class ServiceDataImpl implements ServiceData {

  Jdbc jdbc;

  ServiceDataImpl(Jdbc jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public TableX get(long id) {
    return jdbc.streamRecords(TableX.class, "select id, v from tablex where id = ?", id).findFirst().orElseThrow();
  }

  @Override
  public void set(long id, String value) {
    jdbc.execute("update tablex set v = ? where id = ?", st -> {
      st.setString(1, value);
      st.setLong(2, id);
    });
  }
}
