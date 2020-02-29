# Desafio Dito - Coletor de Dados (Producer)

Desafio da Empresa dito relacionado a um coletor de dados que rastrem comportamento de um usuário ao utilizar um e-comerce.
Este repositório contém a API responsável por publicar os eventos em um broker Apache Kafka.

## Design da Solução

![Arch](https://imgur.com/LuT4eRx)

   
Em que:

1. Uma API recebe as informações, valida o formato e publica em uma fila para um cluster Apache Kafka
2. Uma outra API consome estas informações e grava em cluster Elasticsearch. Esta API consumidora também fornece 
um endpoint para buscar os registros filtrados por tipo de evento

## Tecnologias utilizadas:

* Java 8 com Spring Boot 2
* Apache Kafka
* Elasticsearch
* Docker

### Referências

* [Intro to Apache Kafka with Spring](https://www.baeldung.com/spring-kafka)
* [Intro to Apache Kafka with Spring](https://www.baeldung.com/spring-kafka)
* [Introduction to Spring Data Elasticsearch](https://www.baeldung.com/spring-data-elasticsearch-tutorial)
* [Fabric 8 Maven Plugin for Docker](https://dmp.fabric8.io/)

### 2020 - Lucas de Castro Oliveira