package org.example.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;

public class HelloServer {
    private Server server;

    public void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new HelloServiceImpl())
                .build()
                .start();
        System.out.println("Server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            HelloServer.this.stop();
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloServer server = new HelloServer();
        server.start();
        server.blockUntilShutdown();
    }

    // Implementation of service
    static class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            HelloResponse response = HelloResponse.newBuilder()
                    .setMessage("Hello, " + request.getName())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void lotsOfReplies(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            for (int i = 1; i <= 5; i++) {
                responseObserver.onNext(HelloResponse.newBuilder()
                        .setMessage("Reply " + i + " to " + request.getName())
                        .build());
            }
            responseObserver.onCompleted();
        }

        @Override
        public StreamObserver<HelloRequest> lotsOfGreetings(StreamObserver<HelloResponse> responseObserver) {
            return new StreamObserver<>() {
                StringBuilder names = new StringBuilder();

                @Override
                public void onNext(HelloRequest request) {
                    names.append(request.getName()).append(", ");
                }

                @Override
                public void onError(Throwable t) { }

                @Override
                public void onCompleted() {
                    responseObserver.onNext(HelloResponse.newBuilder()
                            .setMessage("Hello to all: " + names)
                            .build());
                    responseObserver.onCompleted();
                }
            };
        }

        @Override
        public StreamObserver<HelloRequest> bidiHello(StreamObserver<HelloResponse> responseObserver) {
            return new StreamObserver<>() {
                @Override
                public void onNext(HelloRequest request) {
                    responseObserver.onNext(HelloResponse.newBuilder()
                            .setMessage("Hello " + request.getName())
                            .build());
                }

                @Override
                public void onError(Throwable t) { }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }
}
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloServer server = new HelloServer();
        server.start();
    }

}
