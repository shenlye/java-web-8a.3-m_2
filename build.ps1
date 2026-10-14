# Build script (no Maven on this machine, so compile with javac directly)
# Usage: in PowerShell run  .\build.ps1
#   1) compile java sources under src  -> web\WEB-INF\classes
#   2) copy src\db.properties          -> web\WEB-INF\classes
#   3) copy mysql driver               -> web\WEB-INF\lib  (needed at runtime)

$ErrorActionPreference = 'Stop'

$root    = $PSScriptRoot
$src     = Join-Path $root 'src'
$web     = Join-Path $root 'web'
$classes = Join-Path $web 'WEB-INF\classes'
$lib     = Join-Path $root 'lib'
$webLib  = Join-Path $web 'WEB-INF\lib'

Write-Host '>>> prepare directories' -ForegroundColor Cyan
New-Item -ItemType Directory -Force -Path $classes, $webLib | Out-Null

Write-Host '>>> compile java sources' -ForegroundColor Cyan
$javaFiles = Get-ChildItem -Path $src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 --release 8 -cp (Join-Path $lib 'javax.servlet-api-4.0.1.jar') -d $classes $javaFiles
if ($LASTEXITCODE -ne 0) { throw 'compile failed' }

Write-Host '>>> copy config and driver' -ForegroundColor Cyan
Copy-Item (Join-Path $src 'db.properties') $classes -Force
Copy-Item (Join-Path $lib 'mysql-connector-j-8.0.33.jar') $webLib -Force

Write-Host '>>> build finished, output: ' $classes -ForegroundColor Green
