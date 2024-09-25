#cltbet
Aplicação Desktop de uma Casa de Apostas, feita em Java com Swing e JDBC.

## Tecnologias Utilizadas

- **Java com Swing**: Utilizado para desenvolver a interface gráfica da aplicação, proporcionando uma experiência interativa e amigável ao usuário.
  
- **JDBC (Java Database Connectivity)**: Biblioteca Java que permite a interação com o banco de dados PostgreSQL, possibilitando operações como inserção, atualização, deleção e consulta de dados.

- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional utilizado para armazenar os dados da aplicação, como usuários, apostas e eventos. A versão utilizada é 14.1-alpine, que é leve e eficiente.

- **Docker**: Utilizado para criar e gerenciar um ambiente isolado para a aplicação e o banco de dados. O Docker facilita o deployment e a portabilidade da aplicação, permitindo que ela seja executada de maneira consistente em diferentes ambientes.

## Docker Compose

O projeto utiliza um arquivo `docker-compose.yml` para configurar e iniciar o container do banco de dados PostgreSQL. Abaixo está o conteúdo do arquivo:

```yaml
version: '3.8'
services:
  db:
    image: postgres:14.1-alpine
    restart: always
    environment:
      - POSTGRES_USER=dev
      - POSTGRES_PASSWORD=1234
      - POSTGRES_DB=clt_bet
    ports:
      - '5435:5432'
    volumes: 
      - db:/var/lib/postgresql/data
      - ./db/init.sql:/docker-entrypoint-initdb.d/create_tables.sql
volumes:
  db:
    driver: local
```
-----------------------------------------------------------------------------------
Como Ligar o Container Docker com o Banco de Dados
Para iniciar o container Docker com o banco de dados PostgreSQL, siga os passos abaixo:

Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina. Você pode verificar isso executando os comandos:

    docker --version
    docker-compose --version

Navegue até o diretório do seu projeto onde o arquivo docker-compose.yml está localizado.

Execute o seguinte comando para iniciar o container:

    docker-compose up -d

O parâmetro -d permite que o container seja executado em segundo plano.

Verifique se o container está em execução com o comando:

    docker ps

Você deve ver o container do PostgreSQL listado.

Acesse o banco de dados com um cliente PostgreSQL, como o DBeaver ou o pgAdmin, usando as seguintes credenciais:

Host: localhost
Porta: 5435
Usuário: dev
Senha: 1234
Banco de Dados: clt_bet

Ao iniciar o container pela primeira vez, o arquivo init.sql será executado automaticamente para criar as tabelas necessárias.

Agora você está pronto para usar sua aplicação com o banco de dados PostgreSQL rodando em um container Docker!

# Executar sistema

Para executar o sistema, basta executar o arquivo Main, que está localizado na pasta housebet.

![image](https://github.com/user-attachments/assets/73694dc3-5a11-4955-a31f-fbc670bda3c5)

# Arquitetura do Sistema:
![Imagem do WhatsApp de 2024-07-24 à(s) 20 38 51_e2defdab](https://github.com/user-attachments/assets/6aaefb67-e0f3-493d-ac32-79fa8009aa31)

Interface Gráfica (GUI - Graphical User Interface)<br />
Responsabilidade: A camada de Interface Gráfica é onde os usuários interagem diretamente com o sistema. Ela é responsável por apresentar as informações de forma visual e capturar as ações dos usuários, como cliques, preenchimento de formulários, etc.<br />
Componentes: Tipicamente inclui telas, botões, campos de entrada, e outros elementos de interface que permitem a interação do usuário.<br />
Fluxo: As ações realizadas pelo usuário na interface são enviadas para a camada de Serviços para processamento.<br />
<br />
<br />
Serviços<br />
Responsabilidade: A camada de Serviços age como um intermediário entre a Interface Gráfica e a camada de Regras de Negócio. Ela recebe as solicitações vindas da interface gráfica e as encaminha para as Regras de Negócio. Também é responsável por enviar as respostas de volta para a Interface Gráfica.<br />
Componentes: Geralmente, inclui controladores ou serviços REST, que orquestram as operações e garantem que as solicitações do usuário sejam processadas corretamente.<br />
Fluxo: Recebe dados da GUI, processa-os se necessário, e os envia para a camada de Regras de Negócio. Depois, recebe as respostas da camada de Regras de Negócio e as retorna para a GUI.<br />
<br />
<br />
Regras de Negócio (Business Layer)<br />
Responsabilidade: Esta é a camada onde a lógica de negócios do sistema reside. Ela processa as solicitações recebidas da camada de Serviços, aplica as regras de negócio (como validações, cálculos, etc.), e toma decisões baseadas nessas regras.<br />
Componentes: Inclui classes e métodos que encapsulam a lógica de negócio. Pode incluir serviços de domínio, classes de entidade, e outras estruturas que suportam a aplicação das regras de negócio.<br />
Fluxo: Recebe solicitações dos Serviços, processa de acordo com as regras de negócio, e retorna os resultados, que podem ser encaminhados de volta à GUI ou armazenados no banco de dados.<br />
<br />
<br />
SGBD/CRUD (DAO - Data Access Object)<br />
Responsabilidade: A camada DAO é responsável por interagir diretamente com o banco de dados. Ela executa as operações CRUD (Create, Read, Update, Delete) para armazenar, recuperar, modificar ou deletar dados do banco.<br />
Componentes: Inclui classes e métodos que se conectam ao banco de dados e executam as queries SQL. Esta camada abstrai os detalhes de interação com o banco de dados para as outras camadas.<br />
Fluxo: Recebe comandos da camada de Regras de Negócio ou diretamente da camada de Serviços, executa as operações no banco de dados, e retorna os resultados.<br />
<br />
<br />
Database PostgreSQL<br />
Responsabilidade: O banco de dados é onde todas as informações do sistema são armazenadas de forma estruturada. O PostgreSQL é conhecido por ser robusto, seguro e escalável, adequado para aplicações que requerem alta disponibilidade e integridade dos dados.<br />
Componentes: Inclui tabelas, índices, procedures, triggers, entre outros objetos de banco de dados que suportam o armazenamento e a manipulação de dados.<br />
Fluxo: A camada DAO interage diretamente com o PostgreSQL para executar operações de leitura e escrita.<br />
<br />
<br />
STATIC (Classes Responsáveis por Criar as Queries SQL)<br />
Responsabilidade: As classes STATIC são responsáveis por gerar as queries SQL necessárias para interagir com o banco de dados. Elas encapsulam as instruções SQL em métodos reutilizáveis.<br />
Componentes: São classes que contêm métodos estáticos que retornam queries SQL como strings ou, em alguns casos, objetos que representam essas queries.<br />
Fluxo: A camada DAO utiliza essas classes para obter as queries necessárias, que são então executadas no banco de dados.<br />
<br />
<br />

# Banco de Dados - Modelagem
![image](https://github.com/user-attachments/assets/4bcb0727-8916-4e0f-863d-4280cbe05223)

# Diagrama de Classes

![image](https://github.com/user-attachments/assets/ec97b5f0-3757-497e-bd38-ff8323b1ab9b)