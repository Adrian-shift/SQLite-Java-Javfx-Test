# Teste: Empacotamento Nativo de App JavaFX + SQLite (Windows)

Este é um projeto de estudo (Prova de Conceito - PoC) desenvolvido para aprender e validar a arquitetura necessária para transformar um programa **Java + JavaFX + SQLite** em um aplicativo Windows nativo e distribuível (`.exe`), que funciona de forma independente.

O grande diferencial desta abordagem é que **o usuário final não precisa ter o Java (JRE/JDK), IntelliJ, Maven ou SQLite instalados na máquina**. O aplicativo carrega seu próprio "Mini-Java" (via `jlink`) e o banco de dados é embutido na aplicação.

## Tecnologias Utilizadas

*   **Java / JDK:** (Versão moderna, testado com JDK 21+)
*   **JavaFX:** Interface gráfica (gerenciada via Maven)
*   **SQLite JDBC:** Banco de dados local embutido
*   **Maven Wrapper (`mvnw`):** Gerenciamento de dependências e build
*   **jlink:** Ferramenta do JDK para criar uma imagem modular otimizada do Java
*   **jpackage:** Ferramenta do JDK para gerar o executável e o instalador nativo
*   **WiX Toolset (v3.11):** Necessário no ambiente de desenvolvimento para gerar o instalador `.exe` no Windows

## Decisões de Arquitetura Importantes

1.  **Sistema de Módulos (JPMS):** Para que o `jlink` funcione e o empacotamento ocorra com sucesso, o projeto utiliza estritamente o `module-info.java`. O driver do SQLite foi explicitamente exigido no módulo (`requires org.xerial.sqlitejdbc;` e `requires java.sql;`) para evitar falhas de execução no ambiente restrito.
2.  **Caminho do Banco de Dados:** O arquivo `teste.db` não é salvo na pasta de instalação do programa (como `C:\Program Files\`), pois o Windows bloqueia a escrita por segurança. A lógica do Controller foi ajustada para criar a pasta e o banco de dados de forma segura no diretório do usuário (`System.getProperty("user.home") + "\MeuTesteApp"`).

---

## Como executar o projeto (Modo Desenvolvedor)

Se você apenas clonou o repositório e quer testar o código rodando a partir do código-fonte:

1. Abra o terminal (CMD) na raiz do projeto.
2. Execute o Maven Wrapper para baixar as dependências e rodar o JavaFX:
   ```cmd
   .\mvnw.cmd clean javafx:run
