
#Angualr + Nginx in Docker

FROM nginx:1.19-alpine

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*
COPY nginx/nginx.conf /etc/nginx/nginx.conf
## From 'builder' stage copy over the artifacts in dist folder to default nginx public folder
# COPY ./dist /user/share/nginx/html
WORKDIR /usr/share/nginx/html
COPY dist/GipherUI .
CMD ["nginx", "-g", "daemon off;"]