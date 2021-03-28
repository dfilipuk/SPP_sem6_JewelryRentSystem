FROM node:15.12.0

EXPOSE 4200
WORKDIR /app

ENV PATH=/app/node_modules/.bin:$PATH
# Windows only: enable hot reloading
ENV CHOKIDAR_USEPOLLING=true

COPY package.json package.json
COPY package-lock.json package-lock.json

RUN npm ci
RUN npm install -g @angular/cli

COPY . .
ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0"]