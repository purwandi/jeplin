FROM node:14-alpine as builder
WORKDIR /workspace
ADD index.html /workspace/

FROM nginx:alpine
COPY --from=builder /workspace/ /usr/share/nginx/html