#!/bin/sh

protoDirectory=$(cd ../ $(dirname $0); pwd)

docker run --rm \
  -v $(pwd):/out \
  -v $protoDirectory/src/main/proto:/protos \
  pseudomuto/protoc-gen-doc \
  --doc_opt=html,proto-document.html \
  --proto_path=/protos/ \
  com/example/infrastructure/grpc/protobuf/service/campaign_service.proto \
  com/example/infrastructure/grpc/protobuf/type/campaign.proto
