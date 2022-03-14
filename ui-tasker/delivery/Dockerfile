FROM node:16-alpine as dev

WORKDIR /opt/app

# Add bash
RUN apk add --no-cache bash

COPY package.json ./

# Cache when possible...

RUN npm install

COPY src ./src

COPY public ./public

# dynamic environment vars at runtime

COPY env.sh .

RUN ./env.sh

RUN cp env-config.js ./public/

RUN npm run test:coverage

RUN npm run build

CMD ["npm", "start"]

FROM nginx:stable-alpine as prod

# Add bash
RUN apk add --no-cache bash

# Nginx internal static files
RUN mkdir /etc/static_files

# React application
COPY --from=dev /opt/app/build /usr/share/nginx/html

# .env file and shell script tool 
COPY env.sh /usr/share/nginx/html
COPY .env /usr/share/nginx/html

# Nginx configuration
COPY ./delivery/nginx/nginx.conf /etc/nginx/nginx.conf

# Nginx error page: /image-error.png resource has to be accesible in browsers
COPY ./delivery/nginx/files/50x.html /etc/static_files/error/50x.html
COPY ./delivery/nginx/files/image-error.png /etc/static_files/error/image-error.png

EXPOSE 80

# Start Nginx server
CMD ["/bin/bash", "-c", "/usr/share/nginx/html/env.sh && nginx"]
