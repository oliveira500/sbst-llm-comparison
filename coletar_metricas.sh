#!/bin/bash

echo "========================================"
echo "COLETA DE MÉTRICAS COMPARATIVAS"
echo "========================================"
echo ""

# Compila o projeto
echo "1. Compilando o projeto..."
mvn clean compile
echo ""

# Executa os testes do EvoSuite
echo "2. Executando testes do EvoSuite..."
mvn test -Dtest="*ESTest"
echo ""

# Executa os testes do GitHub Copilot
echo "3. Executando testes do GitHub Copilot..."
mvn test -Dtest="*CopilotTest"
echo ""

# Executa os testes do ChatGPT
echo "4. Executando testes do ChatGPT..."
mvn test -Dtest="*ChatGPTTest"
echo ""

# Gera relatório de cobertura JaCoCo
echo "5. Gerando relatório de cobertura JaCoCo..."
mvn jacoco:report
echo ""

# Executa PITest para mutation testing
echo "6. Executando PITest para mutation testing..."
mvn pitest:mutationCoverage
echo ""

echo "========================================"
echo "COLETA DE MÉTRICAS CONCLUÍDA"
echo "========================================"
echo ""
echo "Relatórios gerados:"
echo "- JaCoCo: target/site/jacoco/index.html"
echo "- PITest: target/pit-reports/YYYYMMDDHHMM/index.html"
echo "- SonarCloud: https://sonarcloud.io/dashboard?id=sbst-llm-comparison"