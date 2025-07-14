# Sistema de Vendas de Mercadinho

> Projeto desenvolvido para a disciplina de Programação Orientada a Objetos. Trata-se de uma aplicação de desktop completa para um Ponto de Venda (PDV), implementada em Java com a biblioteca JavaFX. O sistema demonstra a aplicação de conceitos de arquitetura em camadas (DAO), persistência de dados e criação de interfaces gráficas funcionais.
-----
<p align="center">
<img src="https://img.shields.io/badge/STATUS-CONCLU%C3%8DDO-green"
</p>

<p align="center">
<img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white" alt="Java"/>
<img src="https://img.shields.io/badge/JavaFX-07405E?style=for-the-badge\&logo=oracle\&logoColor=white" alt="JavaFX"/>
<img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge\&logo=apachemaven\&logoColor=white" alt="Maven"/>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge\&logo=mysql\&logoColor=white" alt="MySQL"/>
</p>

## ✨ FUNCIONALIDADES PRINCIPAIS

### Autenticação e Níveis de Acesso

  - **Tela de Login Segura**: Controla o acesso ao sistema.
  - **Dois Tipos de Usuário**:
      - **Administrador**: Acesso total ao sistema, incluindo cadastros e relatórios.
      - **Caixa**: Acesso restrito à tela de vendas.

### Painel do Administrador

  - **Gerenciamento de Clientes**: Funcionalidades de Adicionar, Editar, Listar e Remover (CRUD).
  - **Gerenciamento de Produtos**: CRUD completo, incluindo controle de estoque.
  - **Gerenciamento de Usuários**: CRUD completo para os usuários do sistema.
  - **Relatório de Vendas**: Emissão de relatório sintético diário, agrupado por forma de pagamento, com opção de exportar para arquivo de texto (`.txt`).

### Tela de Vendas (Caixa)

  - **Seleção de Cliente**: Permite associar uma venda a um cliente cadastrado ou a um "Consumidor Padrão".
  - **Carrinho de Compras**: Adição de produtos por código de barras, com cálculo automático de subtotal.
  - **Finalização de Venda**: Escolha da forma de pagamento (Dinheiro, PIX, Débito, Crédito).
  - **Atualização de Estoque**: O estoque dos produtos é atualizado automaticamente após cada venda.

-----

## 🚀 TECNOLOGIAS UTILIZADAS

<div align="center"\>

| Categoria     | Tecnologia           | Descrição                                         |
|---------------|----------------------|---------------------------------------------------|
| Linguagem     | Java (JDK 17+)       | Base da lógica de toda a aplicação.               |
| Interface     | JavaFX               | Biblioteca para criação de interfaces gráficas modernas. |
| Build         | Apache Maven         | Gerenciamento de dependências e do ciclo de vida do projeto. |
| Banco de Dados| MySQL                | Sistema de gerenciamento de banco de dados relacional. |
| Conexão DB    | JDBC                 | API padrão do Java para conectividade com bancos de dados. |
| Versionamento | Git & GitHub         | Controle de versão do código-fonte.               |

</div>

-----

## 📂 ESTRUTURA DO PROJETO

```
ProjetoFinalPOO/
├── src/
│   └── main/
│       └── java/
│           ├── Conexão/
│           ├── DAO/
│           ├── Model/
│           ├── Util/
│           └── View/
├── .gitignore
├── pom.xml
└── script_banco.sql
```

-----

## 🛠 COMO RODAR O PROJETO LOCALMENTE

### Pré-requisitos

  - Java JDK 17 ou superior.
  - Servidor de banco de dados MySQL 8.0 ou superior.
  - Apache Maven (geralmente já integrado a IDEs como o NetBeans).

### 1\. Clonar o Repositório

```bash
git clone https://github.com/bexiie/Projeto-Final-POO.git
cd Projeto-Final-POO
```

### 2\. Configurar o Banco de Dados

  - Abra sua ferramenta de gerenciamento MySQL (MySQL Workbench, DBeaver, etc.).
  - Execute o arquivo `script_projeto.sql` que acompanha o projeto. Ele criará o banco `JavaBD`, as tabelas e inserirá os dados iniciais.
  - **Importante:** Se as credenciais do seu MySQL forem diferentes, altere-as no arquivo `src/main/java/Conexão/ConnectionFactory.java` (padrão: `root` / `1234`).

### 3\. Rodar a Aplicação

  - Abra o projeto em uma IDE como o NetBeans ou IntelliJ.
  - Rode o comando "Clean and Build" para que o Maven baixe as dependências.
  - Execute o projeto com a meta específica do Maven para JavaFX:

<!-- end list -->

```bash
mvn javafx:run
```

  - A tela de login será iniciada.

-----

## 🏗️ UML - DIAGRAMA DE CLASSES

<p align="center"\>
<img width="50%" alt="Captura de tela 2025-07-14 055322" src="https://github.com/user-attachments/assets/9e5a20b9-b98a-461b-a054-63261d84077e" />
</p>

-----

## 🖼 SCREENSHOTS DO SISTEMA

<p align="center"\><i>Tela de login</i\></p\>
<p align="center"\>
<img width="50%" alt="Captura de tela 2025-07-14 053654" src="https://github.com/user-attachments/assets/9c02c51e-e974-4a80-8f76-f142ab4711ed" />
</p>

<p align="center"\><i>Painel de Controle do Administrador</i></p>
<p align="center"\>
<img width="50%" alt="Captura de tela 2025-07-14 053715" src="https://github.com/user-attachments/assets/7d17e958-aacc-4b9c-aa13-a638f7f3a6ff" />
</p>

<p align="center"\><i>Tela de Gerenciamento de Clientes com Formulário de Edição</i></p>
<p align="center"\>
<img width="50%" alt="imagem_2025-07-14_055834915" src="https://github.com/user-attachments/assets/154ef842-b7f6-4aad-8866-107b3f9d921e" />
</p>

<p align="center"\><i>Tela de Gerenciamento de Produtos com Formulário de Edição</i></p>
<p align="center"\>
<img width="50%" alt="Captura de tela 2025-07-14 060554" src="https://github.com/user-attachments/assets/2209fca1-cee4-4e51-86aa-b522859b221f" />
</p>

<p align="center"\><i>Tela de Gerenciamento de Usuários com Formulário de Edição</i></p>
<p align="center"\>
<img width="50%" alt="Captura de tela 2025-07-14 060612" src="https://github.com/user-attachments/assets/e787566e-02f9-4349-b01c-f045b5b92f9e" />
</p>

<p align="center"\><i>Tela de Relatório Sintético de Vendas</i></p>
<p align="center"\>
<img width="50%" alt="Captura de tela 2025-07-14 060719" src="https://github.com/user-attachments/assets/cb08528c-e02a-4c53-a268-6b2c213952a6" />
</p>

<p align="center"\><i>Tela de Formulário de Vendas</i></p>
<p align="center"\>
<img width="50%" alt="Captura de tela 2025-07-14 061430" src="https://github.com/user-attachments/assets/7f4d81bd-9d20-4d64-8f83-3adad62c8794" />
</p>

<p align="center"\><i>Tela de Seleção de Clientes</i></p>
<p align="center"\>
<img width="50%" alt="imagem_2025-07-14_061725151" src="https://github.com/user-attachments/assets/7d73d3e0-8c3b-4ad1-8a5b-82044c93106c" />
</p>

<p align="center"\><i>Tela de Forma de Pagamento</i></p>
<p align="center"\>
<img width="50%" alt="imagem_2025-07-14_062127522" src="https://github.com/user-attachments/assets/ca74d727-175a-4b70-b812-4a3b66456b22" />
</p>
