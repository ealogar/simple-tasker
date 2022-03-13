FROM node:16-alpine as dev

WORKDIR /opt/app


COPY package.json ./

RUN npm install

COPY src ./src

COPY public ./public


RUN npm run test:coverage

RUN npm run build

CMD ["npm", "start"]

FROM nginx:stable-alpine as prod

# Nginx internal static files
RUN mkdir /etc/static_files

# React application
COPY --from=dev /opt/app/build /usr/share/nginx/html

# Nginx configuration
COPY ./delivery/nginx/nginx.conf /etc/nginx/nginx.conf

# Nginx error page: /image-error.png resource has to be accesible in browsers
COPY ./delivery/nginx/files/50x.html /etc/static_files/error/50x.html
COPY ./delivery/nginx/files/image-error.png /etc/static_files/error/image-error.png

EXPOSE 80
