# Desafio DevelCode
## Jornada de desenvolvimento:

Desafio proposto pela empresa Develcode, no qual consiste no seguinte case:

### Criação de pedido de compra:
Criar dois microsserviços, checkout e payment_gateway, onde um pedido realizado deve ser cancelado automaticamente caso o pagamento falhe.
O microserviço em Node deve ser desenvolvido utilizando TypeScript, preferencialmente com o framework NestJS.
O microserviço em Java deve ser implementado na versão Java 17+, utilizando o framework Spring Boot na versão 3.3.4+.
Ambos microserviços devem garantir:
- Persistência de todas as interações.
Definir e implementar schemas para o banco de dados, garantindo normalização se relacional ou estrutura coerente se NoSQL.
- Implementar controle de exceções.
- Utilização de patterns apropriados.
- Comunicação assíncrona.
- Mecanismos de retry em caso de falhas transitórias durante a comunicação assíncrona.
- Aplicação dos princípios do SOLID para garantia de um código modular e escalável.
- Pelo menos 60% de cobertura com testes unitários.
- Documentação básica, incluindo endpoints, exemplos de requisições/respostas e configuração do ambiente para execução.
- Logging adequado para rastreio de operações e erros.

<br></br>

## Microserviço Checkout

Foi construído em Node+TS juntamente com o framework NestJS, com mecanismos assíncronos, retry para falhas, aplicação de testes unitários, controle de exceções, BDD, princípios SOLID e sistema de logging.
Bibliotecas e ferramentas utilizadas:
- Axios para cuidar da comunicação entre os microsserviços.
- Axios-Retry para realizar o mecanismo de retry, quando houver falhas.
- Para testes unitários e relatório de coverage: [Jest](https://jestjs.io).
- Swagger para documentação dos endpoints criados

<br></br>

## Payment Gateway

Foi construído em Java juntamente com o framework Spring boot no formato BDD, ACID para transações, princípios SOLID, banco de dados in memory, sistema de seed e sistema de logging.
Bibliotecas e ferramentas utilizadas:
- H2-Database para banco de dados "in memory"
- Lombok - para facilitar o desenvolvimento e criação de logging.
- JUnit para testes unitários
- Swagger para documentação dos endpoints criados