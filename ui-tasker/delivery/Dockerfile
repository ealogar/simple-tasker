
FROM node:16-3-1-alpine as dev

WORKDIR /opt/app


COPY package.json ./

RUN npm install

RUN npm run test:coverage

CMD ["npm", "start"]

FROM node:16-3-1-alpine as builder

WORKDIR /opt/app

COPY --from=dev /opt/app/package.json ./
COPY --from=dev /opt/app/package.json ./

COPY package.json ./

RUN npm run build

FROM nginx:1.18-0-alpine as prod

# Nginx internal static files
RUN mkdir /etc/static_files

# React application
COPY --from=builder /opt/app/build /usr/share/nginx/html

# Nginx configuration
COPY ./delivery/nginx/nginx.conf /etc/nginx/nginx.conf

# Nginx error page: /image-error.png resource has to be accesible in browsers
COPY ./delivery/nginx/files/50x.html /etc/static_files/error/50x.html
COPY ./delivery/nginx/files/image-error.png /etc/static_files/error/image-error.png

EXPOSE 80

