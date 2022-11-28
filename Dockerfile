FROM php:7.1-apache

RUN docker-php-ext-install pdo pdo_mysql

COPY ./com/database/dump.sql /docker-entrypoint-initdb.d/