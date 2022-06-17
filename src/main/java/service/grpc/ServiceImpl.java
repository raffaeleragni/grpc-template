package service.grpc;

import io.grpc.stub.StreamObserver;
import service.ServiceGrpc;
import service.ServiceOuterClass.Empty;
import service.ServiceOuterClass.GetRequest;
import service.ServiceOuterClass.GetResponse;
import service.ServiceOuterClass.SetRequest;
import service.data.ServiceData;

class ServiceImpl extends ServiceGrpc.ServiceImplBase implements Service {

  ServiceData data;

  ServiceImpl(ServiceData data) {
    this.data = data;
  }

  @Override
  public void get(GetRequest request, StreamObserver<GetResponse> output) {
    var result = data.get(request.getId());
    var response = GetResponse.newBuilder()
      .setValue(result.v())
      .build();
    output.onNext(response);
    output.onCompleted();
  }

  @Override
  public void set(SetRequest request, StreamObserver<Empty> output) {
    data.set(request.getId(), request.getValue());

    output.onNext(Empty.getDefaultInstance());
    output.onCompleted();
  }
}
