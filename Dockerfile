FROM node:lts-alpine3.10

ARG SECRET
ARG MONGODB_URI

ENV SECRET=$SECRET
ENV MONGODB_URI=$MONGODB_URI

RUN addgroup -S appuser && adduser -S appuser -G appuser

RUN mkdir -p /tmp/build; \
    mkdir -p /app;

WORKDIR /app

COPY . .

RUN npm install

EXPOSE 3000/tcp

ENTRYPOINT ["npm","run","start"]
