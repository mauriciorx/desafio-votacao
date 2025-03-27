# Gerenciamento de Votos por Sessão de Pauta em uma Assembleia
## Descrição

Este projeto implementa uma API para gerenciar votos em sessões de pauta definidas em uma assembleia. A aplicação permite criar e consultar associados, pautas, sessões de votação, votos e extrair os resultados. A API foi desenvolvida utilizando as tecnologias Spring Boot, JPA, Lombok, Docker, PostgreSQL, Swagger, JUnit, Mockito, WebMvcTest, Feign e Java 21.

## Tecnologias Utilizadas

- Spring Boot: Framework principal para a construção da aplicação;
- JPA (Java Persistence API): Para mapeamento objeto-relacional e persistência de dados;
- Lombok: Biblioteca para redução de código boilerplate (getter, setter, construtores, etc.);
- Docker: Para conteinerização do banco de dados PostgreSQL;
- PostgreSQL: Banco de dados relacional utilizado para armazenar as informações;
- Swagger: Para documentação interativa da API;
- JUnit & Mockito: Frameworks de testes para garantir a qualidade e o correto funcionamento da aplicação;
- WebMvcTest: Para testar os controllers da aplicação de forma isolada;
- Feign: Para comunicação com outros serviços (CPF);
- Java 21: A versão do Java utilizada para desenvolvimento.

## Funcionalidades

- Criação e consulta de associados;
- Criação e consulta de pautas para votação;
- Criação e consulta de sessões de votação com duração específica;
- Criação de votos únicos por associado;
- Geração e validação de CPF;
- Extraçao de resultados da votação.

## Regras
- Cada associado deverá estar vinculado a um CPF distinto e válido;
- Cada associado poderá votar apenas uma vez em cada pauta;
- Cada sessão tem um tempo de atividade, após o encerramento não são permitidos mais votos;
- Cada pauta poderá ter apenas uma sessão ativa;

## Como Executar
### Pré-requisitos

- Docker: Para rodar o banco de dados PostgreSQL em um container.
- Java 21: Certifique-se de ter o JDK 21 para rodar a aplicação localmente.
- Maven: Para gerenciar dependências e construir o projeto.

### Passos

1. Clone o repositório;
`````
git clone https://github.com/mauriciorx/desafio-votacao.git
cd desafio-votacao
`````
2. Suba o Banco de Dados com Docker;
`````
docker-compose up -d
`````
3. Execute e acesse a aplicação.
`````
API - http://localhost:8080
Swagger - http://localhost:8080/swagger-ui/index.html
`````
## Decisões Técnicas
- A API foi estruturada utilizando a arquitetura em camadas, que é uma das abordagens mais comuns em projetos Spring Boot. A escolha por esse padrão arquitetural visa promover uma separação de responsabilidades e facilitar a manutenção e escalabilidade do sistema;
- A API foi projetada com versionamento para garantir a compatibilidade com versões anteriores e permitir a evolução do sistema sem quebrar integrações existentes. O versionamento da API é especificado diretamente na URL, trazendo mais clareza, controle de compatibilidade e facilidade de manutenção;
- Para a documentação da API foi utilizado o Swagger, pois este fornece uma interface interativa para testar manualmente os endpoints da API. Ele é útil tanto para documentação quanto para testar rapidamente a funcionalidade da API em tempo de execução;
- O Feign foi escolhido para simular a comunicação com o serviço de consulta de validade de CPF. Sua simplicidade e integração nativa com o Spring Cloud, permite que a comunicação com serviços externos seja facilmente simulada e testada, mantendo o código limpo e legível.



