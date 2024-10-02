# Fluxus API V2

API de persistência de dados do software Fluxus Desktop com a base de dados MySQL

## Pré-requisitos

- Java 21
- Git
- Docker

## Criar as variáveis de ambiente

##### Na raiz do projeto, executar: 

```sh
echo -e "JWT_SECRET=chave-privada\nDATABASE_PASSWORD=senha-do-banco-de-dados\nDATABASE_USER=root\nDATABASE_URL=jdbc:mysql://mysqldb:3306/fluxus_db" > .env
```

* Substituir `chave-privada` por sua chave JWT privada;
* Substituir `senha-do-banco-de-dados` pela senha deseja atribuir ao usuário `root` do banco de dados.


## Build e Execução

```sh
docker-compose down -v --rmi all --remove-orphans
```

```sh
docker-compose up --force-recreate -d
```

## Caso tenha algum problema:

Após executar o `docker-compose down`, force a construção sem cache antes de executar `docker-compose up` com o comando:

```sh
docker-compose build --no-cache
```

## Documentação online (OpenAPI)

http://localhost:8080/swagger-ui/index.html