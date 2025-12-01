# FreteGen – Sistema de Cálculo de Frete com Melhor Envio

O **FreteGen** é uma aplicação Java Spring Boot desenvolvida para:

- Autenticação de usuários (JWT)
- Cadastro e gestão de produtos
- Cadastro de clientes
- Cálculo de frete utilizando a API do Melhor Envio
- Retorno de cotações simplificadas (transportadora, serviço, preço e prazo)

O objetivo principal é oferecer um backend estruturado capaz de simular a rotina real de lojas virtuais, permitindo comparar preços de envio e obter informações consolidadas em um só lugar.

---

# 1. Arquitetura do Projeto

O projeto segue uma arquitetura organizada em camadas:


Além disso:

- Utiliza **Spring Security** para autenticação JWT  
- Usa **JPA/Hibernate** com UUID  
- Integra-se com a **API do Melhor Envio** via RestTemplate (não reativo)

---

# 2. Domínio da Aplicação

Atualmente, o sistema trabalha com três domínios principais:

---

## 2.1. Usuários (Clients)

Responsáveis por autenticação e autorização.

**Campos:**
- id (UUID)
- login
- password
- role (ADMIN/USER)

---

## 2.2. Produtos (Products)

Produtos possuem:

- Peso  
- Dimensões (altura, largura, comprimento)  
- Valor declarado  
- Quantidade  
- Referência ao usuário que cadastrou  

Essas informações são utilizadas para calcular frete.

---

## 2.3. Cotações de Frete (ShippingData)

Armazena a resposta recebida do Melhor Envio, contendo:

- transportadora  
- serviço  
- preço  
- prazo de entrega  
- CEP origem/destino  
- payload bruto da API para auditoria  

---

# 3. Endpoints Disponíveis

A seguir estão listados todos os endpoints implementados no projeto, organizados por domínio e explicando claramente o propósito de cada um.

---

# 3.1. Autenticação

### **POST /auth/register**
Cria um novo usuário no sistema.

**Body esperado:**

{
  "login": "user@email.com",
  "password": "123456"
}


POST /auth/login
Autentica o usuário e retorna um token JWT.
Resposta:

{
  "token": "jwt-token-aqui"
}

# 3.2. Produtos
POST /products

Cadastra um produto associado ao usuário logado.

Exemplo de body:

{
  "name": "Camiseta",
  "description": "Produto premium",
  "weightKg": 0.5,
  "lengthCm": 30,
  "heightCm": 5,
  "widthCm": 20,
  "declaredValue": 80,
  "quantity": 10
}


GET /products/{productName}
Busca um produto pelo nome.
Usado internamente no cálculo de frete.

DELETE /products/{id}
Remove um produto especificado pelo usuário.

# 3.3. Cálculo de Frete (Melhor Envio)

GET /freight/{productName}?destinationZip=XXXXX-XXX
Realiza o cálculo do frete com base nas informações do produto.

Fluxo executado por este endpoint:
Busca o produto pelo nome
Monta o payload no formato exigido pelo Melhor Envio
Chama POST /api/v2/me/shipping/calculate
Trata a resposta
Retorna apenas os dados relevantes para o cliente

Exemplo de resposta simplificada:

[
 {
    "carrier": "Correios",
    "service": "PAC",
    "price": 32.90,
    "deliveryTime": 7
  },
  {
    "carrier": "Jadlog",
    "service": "Package",
    "price": 28.50,
    "deliveryTime": 5
  }
  
# 4. Integração com Melhor Envio

O sistema usa o endpoint oficial:

POST https://melhorenvio.com.br/api/v2/me/shipping/calculate
O payload enviado contém:
CEP de origem
CEP de destino
Dimensões
Peso
Valor declarado

A API retorna uma lista com dezenas de ofertas de frete, filtradas e convertidas pelo backend.


# 5. Fluxo Completo do Cálculo de Frete

Cliente chama:

GET /freight/{productName}?destinationZip=01001-000
Backend busca o produto
Backend monta payload para Melhor Envio
Backend envia requisição para /calculate
API retorna várias opções
Sistema aplica transformação para um DTO padronizado
Retorno minimalista ao cliente

# 6. Tecnologias Utilizadas
   
Java 17
Spring Boot 3
Spring Security + JWT
Spring Web
JPA / Hibernate
PostgreSQL ou MySQL
RestTemplate
Lombok
Melhor Envio API v2

# 7. Como subir o projeto
   
# 1. Configurar application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/fretes
spring.datasource.username=postgres
spring.datasource.password=123

melhorenvio.api.key=SEU_TOKEN_AQUI
melhorenvio.origin.zip=89110-000

# 2. Rodar a aplicação:
mvn spring-boot:run
