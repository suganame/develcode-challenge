![badge-java](https://img.shields.io/badge/Java-v17.0.12-green)
![badge-maven](https://img.shields.io/badge/Maven-v3.4.2-blue)

# Payment Gateway
Este microsserviço tem como finalidade efetuar o pedido enviado do microsserviço de Checkout. 

## Pré-requisitos
- Jdk-17.0.12
- Maven

## 🚀 Passo a Passo: Execução do projeto
Partido da premissa que já exista o JDK e o maven instalado, podemos iniciar o passo a passo para executar o projeto.
Após abrir o projeto na IDE de preferencia, caso o Maven esteja devidamente configurado, automaticamente baixará as dependências necessárias do projeto. Caso isso não aconteça, rode o comando:
```
mvn install
```

Com as dependências baixadas, podemos já rodar o projeto localmente através do comando:

```
mvn spring-boot:run
```
Após isso, o projeto já executará a criação do banco em memória H2-Database e aplicará as seeds necessárias para o procedimento.

Para acessar o console do H2 basta acessar através do navegador a rota http://localhost:8080/h2-console e adicionar as informações de acordo com o arquivo application.properties que está localizado dentro do projeto.

## Testes unitários: Como executar?
Atualmente o projeto já conta com testes unitários desenvolvidos e com cobertura superior a 90%. Para verificar, basta executar o comando:
```
mvn test
```
Com isso, será executado todos os testes unitários criados e também criará os arquivos de cobertura, que podem ser acessados no diretório do projeto, na pasta target.
