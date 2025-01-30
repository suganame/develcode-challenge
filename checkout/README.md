![badge-nodejs](https://img.shields.io/badge/Node-v23.6.1-green)
![badge-nestjs](https://img.shields.io/badge/Nest-v11.0.1-darkred)

# Microsserviço Checkout
Este microsserviço tem como finalidade enviar o pedido para o microsserviço Payment Gateway. 

## Pré-requisitos
- Node v23.6.1

## 🚀 Passo a Passo: Execução do projeto
Partido da premissa que já exista o Node instalado, podemos iniciar o passo a passo para executar o projeto.

Abrir um terminal no caminho do projeto e executar o seguinte comando para que as dependencias sejam baixadas:

```
npm install
```

Dentro do projeto, também teremos que configurar as variáveis de ambiente para a chamada do gateway.

Portanto, crie um arquivo .env (caso não exista), na raiz do projeto e mapeie a URL do microsserviço do Gateway. Siga o exemplo do arquivo .env.sample.

Com as pré configurações feitas, é hora de rodar o projeto. Para isso basta executar o comando:

```
npm run start
```

Este comando efetuará o build do projeto e o executará.

## Testes unitários: Como executar?
Atualmente o projeto já conta com testes unitários desenvolvidos e com cobertura superior a 90%. Para verificar, basta executar o comando:

Para executar os testes sem gerar o arquivo de cobertura:
```
npm run test
```

Para executar os testes com o arquivo de cobertura:
```
npm run test:cov
```

Com isso, será executado todos os testes unitários criados e também criará os arquivos de cobertura caso tenha rodado o segundo comando, que podem ser acessados no diretório do projeto, na pasta coverage.
