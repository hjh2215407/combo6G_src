package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.dabinsystems.pact_app.DataBinderMapperImpl());
  }
}
