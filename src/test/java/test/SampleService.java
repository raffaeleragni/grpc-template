package test;

import io.grpc.examples.ProtoSample;
import io.grpc.examples.ProtoSample.Result;
import io.grpc.examples.ProtoSample.SearchResponse;
import io.grpc.examples.SearchServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.util.List;

class SampleService extends SearchServiceGrpc.SearchServiceImplBase {

  @Override
  public void search(ProtoSample.SearchRequest request, StreamObserver<ProtoSample.SearchResponse> responseObserver) {
    var response = SearchResponse.newBuilder()
      .addAllResults(List.of(Result.newBuilder()
        .setTitle("title")
        .setUrl("url")
        .build()))
      .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

}
