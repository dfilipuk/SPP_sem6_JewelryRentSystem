FROM node:15.12.0

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json /app/package.json
COPY package-lock.json /app/package-lock.json

RUN npm ci
RUN npm install -g @angular/cli

COPY . /app

ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0"]