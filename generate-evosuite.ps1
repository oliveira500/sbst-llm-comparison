# Script otimizado para gerar testes com EvoSuite
# Uma única invocação do Maven com todas as classes (muito mais eficiente)

Write-Host "========================================" -ForegroundColor Green
Write-Host "Gerando testes com EvoSuite (Otimizado)" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Lista de todas as classes de validadores
$classes = @(
    "br.ufjf.ead.validator.EmailValidator",
    "br.ufjf.ead.validator.PasswordValidator",
    "br.ufjf.ead.validator.CreditCardValidator",
    "br.ufjf.ead.validator.DateValidator",
    "br.ufjf.ead.validator.PhoneNumberValidator",
    "br.ufjf.ead.validator.UrlValidator",
    "br.ufjf.ead.validator.CepValidator",
    "br.ufjf.ead.validator.DocumentFormatter",
    "br.ufjf.ead.validator.CpfValidator",
    "br.ufjf.ead.validator.CnpjValidator"
)

# Construir string separada por vírgulas
$classesList = $classes -join ","

Write-Host "Classes a processar: $($classes.Count)" -ForegroundColor Cyan
Write-Host "Total de classes: $classesList" -ForegroundColor Gray
Write-Host ""

# ========================================
# Etapa 1: Gerar testes (uma única chamada Maven)
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Etapa 1/3: Gerando testes com EvoSuite" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Tempo estimado: ~15-20 minutos" -ForegroundColor Yellow
Write-Host ""

& mvn evosuite:generate "-Dclasses=$classesList" "-Dcriterion=LINE:BRANCH" "-DtimeInMinutesPerClass=2"

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "[AVISO] EvoSuite retornou código de erro, mas alguns testes podem ter sido gerados." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Etapa 2/3: Compilando testes gerados" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
& mvn test-compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERRO] Falha ao compilar testes." -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Etapa 3/3: Executando testes EvoSuite" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
& mvn test "-Dtest=*ESTest"

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Conclusao: Geracao de testes concluida!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
