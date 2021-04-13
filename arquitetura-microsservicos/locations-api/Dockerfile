FROM nimmis/apache-php5

# install composer
RUN cd /bin && curl -sS https://getcomposer.org/installer | php && mv composer.phar composer && chmod 777 composer

COPY . /var/www/html/

WORKDIR /var/www/html

RUN composer install --no-plugins --no-scripts && rm index.html