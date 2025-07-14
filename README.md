# Tabela FIPE: Consulta de Preços de Veículos

O **Tabela FIPE** é uma aplicação Java desenvolvida com **Spring Boot** que permite consultar preços de veículos (carros, motos e caminhões) através da API oficial da Tabela FIPE. O sistema oferece uma interface de linha de comando intuitiva para buscar informações detalhadas sobre veículos, incluindo valores de mercado, especificações técnicas e histórico por ano/modelo.

---

## Funcionalidades Principais

- **Consulta Multi-categoria**: Suporte para carros, motos e caminhões da Tabela FIPE oficial.
- **Busca Inteligente**: Filtragem de modelos por nome com busca parcial (case-insensitive).
- **Informações Detalhadas**: Exibição completa de dados do veículo incluindo:
  - Modelo completo
  - Ano de fabricação
  - Valor de mercado atualizado
  - Tipo de combustível
  - Código FIPE oficial
- **Interface Intuitiva**: Menu interativo com navegação por códigos e opções numeradas.
- **Formatação Profissional**: Apresentação dos resultados em tabelas organizadas com bordas ASCII.
- **Consulta por Ano**: Histórico completo de preços por ano/modelo do veículo selecionado.

---

## Tecnologias Utilizadas

- **Linguagem**: Java 21 (com Preview Features habilitadas)
- **Framework**: Spring Boot 3.5.3
- **Gerenciamento de Dependências**: Maven
- **Consumo de API**: HttpClient nativo do Java (java.net.http)
- **Processamento JSON**: Jackson Databind 2.17.0
- **Anotações**: Lombok para redução de boilerplate
- **Arquitetura**: Clean Architecture com separação de responsabilidades

---

## API Utilizada

### Tabela FIPE API
- **URL Base**: `https://parallelum.com.br/fipe/api/v1/`
- **Endpoints principais**:
  - `/carros/marcas` - Lista todas as marcas de carros
  - `/motos/marcas` - Lista todas as marcas de motos
  - `/caminhoes/marcas` - Lista todas as marcas de caminhões
  - `/{categoria}/marcas/{codigo}/modelos` - Modelos por marca
  - `/{categoria}/marcas/{codigo}/modelos/{modelo}/anos` - Anos disponíveis
  - `/{categoria}/marcas/{codigo}/modelos/{modelo}/anos/{ano}` - Dados específicos

### Formato de Resposta
```json
{
  "Valor": "R$ 85.000,00",
  "Modelo": "CIVIC 1.8 LXS 16V FLEX 4P AUT.",
  "AnoModelo": 2012,
  "Combustivel": "Flex",
  "CodigoFipe": "001004-9"
}
```

---

## Estrutura do Projeto

```
src/main/java/com/henr1que/tabelafipe/
├── TabelaFipeApplication.java          # Classe principal Spring Boot
├── model/
│   ├── Dados.java                      # Record para wrapper de modelos
│   ├── DadosItem.java                  # Record para itens (marcas/modelos)
│   └── Veiculo.java                    # Record para dados do veículo
├── principal/
│   └── Main.java                       # Lógica principal da aplicação
└── service/
    ├── ConsumoAPI.java                 # Serviço para consumo da API
    ├── ConverteDados.java              # Implementação da conversão JSON
    └── IConverteDados.java             # Interface para conversão de dados
```

---

## Como Funciona

### Fluxo Principal
1. **Seleção de Categoria**: O usuário escolhe entre carros, motos ou caminhões
2. **Listagem de Marcas**: Exibe todas as marcas disponíveis com códigos
3. **Seleção da Marca**: Usuário informa o código da marca desejada
4. **Busca de Modelos**: Lista todos os modelos disponíveis da marca
5. **Filtragem**: Usuário digita parte do nome para filtrar modelos
6. **Seleção do Modelo**: Escolha do código do modelo específico
7. **Consulta por Anos**: Exibe preços e especificações para todos os anos disponíveis

### Exemplo de Uso
```
### OPÇÕES ###:
1 - CARRO
2 - MOTO
3 - CAMINHÃO
ESCOLHA UMA DAS OPÇÕES ACIMA: 1

### MARCAS DISPONÍVEIS ###:
Código: 1 - Nome: Acura
Código: 2 - Nome: Agrale
Código: 3 - Nome: Alfa Romeo
...

Digite o código da marca desejada: 21

### MODELOS DISPONÍVEIS ###:
Código: 1 - Nome: CIVIC 1.8 LXS 16V FLEX 4P AUT.
Código: 2 - Nome: CIVIC 2.0 LXR 16V FLEX 4P AUT.
...

Digite um trecho do nome do veículo para consulta: civic

### VEÍCULOS ENCONTRADOS ###:
Código: 1 - Nome: CIVIC 1.8 LXS 16V FLEX 4P AUT.
Código: 2 - Nome: CIVIC 2.0 LXR 16V FLEX 4P AUT.
```

---

## Configuração e Execução

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+
- Conexão com internet para acessar a API

### Clonando o Repositório
```bash
git clone https://github.com/seu-usuario/tabela-fipe.git
cd tabela-fipe
```

### Executando a Aplicação
```bash
# Via Maven
./mvnw spring-boot:run

# Ou compilando primeiro
./mvnw clean compile
./mvnw spring-boot:run

# Ou gerando JAR
./mvnw clean package
java -jar target/tabela-fipe-0.0.1-SNAPSHOT.jar
```

### Configuração de IDE
Para IDEs como IntelliJ IDEA ou Eclipse:
1. Importe o projeto como Maven
2. Configure Java 21 como SDK
3. Habilite preview features nas configurações do compilador
4. Execute a classe `TabelaFipeApplication.main()`

---

## Arquitetura e Padrões

### Clean Architecture
- **Model**: Records imutáveis para representação de dados
- **Service**: Camada de serviços para lógica de negócio
- **Principal**: Camada de apresentação e controle de fluxo

### Padrões Utilizados
- **Strategy Pattern**: Interface `IConverteDados` para conversão
- **Record Pattern**: Uso de records para DTOs imutáveis
- **Dependency Injection**: Injeção via Spring Boot
- **Error Handling**: Tratamento de exceções de rede e parsing

### Recursos Java 21
- **String Templates**: Interpolação de strings com STR.""
- **Pattern Matching**: Switch expressions melhoradas
- **Records**: Estruturas de dados imutáveis
- **HttpClient**: Cliente HTTP nativo

---

## Tratamento de Erros

### Cenários Cobertos
- **Erro de Conexão**: Timeout ou falha na API
- **Dados Inválidos**: Códigos inexistentes ou malformados
- **Parsing JSON**: Falha na conversão de dados
- **Busca Vazia**: Nenhum resultado encontrado para o filtro

### Exemplo de Tratamento
```java
try {
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
} catch (IOException | InterruptedException e) {
    throw new RuntimeException("Erro ao obter dados da API: " + e.getMessage(), e);
}
```

---

## Dependências Principais

| Dependência | Versão | Propósito |
|-------------|--------|-----------|
| Spring Boot Starter | 3.5.3 | Framework base e auto-configuração |
| Jackson Databind | 2.17.0 | Conversão JSON para objetos Java |
| Lombok | - | Redução de boilerplate com anotações |
| Spring Boot Test | 3.5.3 | Testes unitários e integração |

---

## Exemplos de Saída

### Resultado Formatado
```
### RESULTADOS POR ANO ###
================================================================================
┌────────────────────────────────────────────────────────────────────────────────┐
│ Modelo              : CIVIC 1.8 LXS 16V FLEX 4P AUT.                           │
│ Ano                 : 2012                                                     │
│ Valor               : R$ 85.000,00                                             │
│ Combustível         : Flex                                                     │
│ Código FIPE         : 001004-9                                                 │
└────────────────────────────────────────────────────────────────────────────────┘
```

---

## Melhorias Futuras

### Performance
- Implementar cache para marcas e modelos
- Pool de conexões HTTP para múltiplas requisições
- Processamento assíncrono com CompletableFuture

### UX/UI
- Interface gráfica com JavaFX ou Swing
- Aplicação web com Spring MVC
- API REST para integração com front-end

### Funcionalidades
- Gráficos de evolução de preços
- Comparação entre modelos similares
- Alertas de variação de preços
- Integração com outras APIs de veículos

---

## Desenvolvido por

**Henrique Oliveira dos Santos**  
[LinkedIn](https://www.linkedin.com/in/dev-henriqueo-santos/)

---
