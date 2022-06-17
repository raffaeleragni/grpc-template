package service.grpc;

import io.grpc.stub.StreamObserver;
import service.ServiceGrpc;
import service.ServiceOuterClass.Request;
import service.ServiceOuterClass.Response;

class ServiceImpl extends ServiceGrpc.ServiceImplBase implements Service {

  @Override
  public void call(Request request, StreamObserver<Response> output) {
    var response = Response.getDefaultInstance();
    output.onNext(response);
    output.onCompleted();
  }

}
