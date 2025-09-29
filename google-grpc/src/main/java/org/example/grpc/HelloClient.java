package org.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class HelloClient {
    private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;
    private final HelloServiceGrpc.HelloServiceStub asyncStub;

    public HelloClient(ManagedChannel channel) {
        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
        asyncStub = HelloServiceGrpc.newStub(channel);
    }

    public void unaryCall() {
        HelloRequest request = HelloRequest.newBuilder().setName("Alice").build();
        HelloResponse response = blockingStub.sayHello(request);
        System.out.println("Unary Response: " + response.getMessage());
    }

    public void serverStreamingCall() {
        HelloRequest request = HelloRequest.newBuilder().setName("Bob").build();
        blockingStub.lotsOfReplies(request).forEachRemaining(
                resp -> System.out.println("Server Stream: " + resp.getMessage()));
    }

    public void clientStreamingCall() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<HelloRequest> requestObserver = asyncStub.lotsOfGreetings(
                new StreamObserver<>() {
                    @Override
                    public void onNext(HelloResponse value) {
                        System.out.println("Client Stream Response: " + value.getMessage());
                    }

                    @Override
                    public void onError(Throwable t) { }

                    @Override
                    public void onCompleted() {
                        latch.countDown();
                    }
                }
        );

        requestObserver.onNext(HelloRequest.newBuilder().setName("Charlie").build());
        requestObserver.onNext(HelloRequest.newBuilder().setName("Diana").build());
        requestObserver.onNext(HelloRequest.newBuilder().setName("Eve").build());
        requestObserver.onCompleted();

        latch.await(5, TimeUnit.SECONDS);
    }

    public void bidiStreamingCall() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StreamObserver<HelloRequest> requestObserver = asyncStub.bidiHello(
                new StreamObserver<>() {
                    @Override
                    public void onNext(HelloResponse value) {
                        System.out.println("Bidi Response: " + value.getMessage());
                    }

                    @Override
                    public void onError(Throwable t) { }

                    @Override
                    public void onCompleted() {
                        latch.countDown();
                    }
                }
        );

        requestObserver.onNext(HelloRequest.newBuilder().setName("Frank").build());
        requestObserver.onNext(HelloRequest.newBuilder().setName("Grace").build());
        requestObserver.onNext(HelloRequest.newBuilder().setName("Heidi").build());
        requestObserver.onCompleted();

        latch.await(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        HelloClient client = new HelloClient(channel);

        client.unaryCall();
        client.serverStreamingCall();
        client.clientStreamingCall();
        client.bidiStreamingCall();

        channel.shutdown();
    }
}
