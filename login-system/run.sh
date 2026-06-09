#!/bin/bash
# Script para compilar e executar o projeto Java Swing.
# Executa tudo dentro da pasta login-system para manter o banco de dados no projeto correto.

# Obtém o diretório do próprio script
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

echo "Compilando os arquivos Java..."

# Compila com a estrutura MVC
javac -cp "lib/*:src/controller:src/model:src/dao:src/view" src/controller/*.java src/model/*.java src/dao/*.java src/view/*.java

if [ $? -eq 0 ]; then
    echo "Compilação bem-sucedida! Iniciando a aplicação..."
    # Variáveis de compatibilidade para Linux/Wayland e gerenciadores de janela tiling (ex: Niri, Sway)
    export _JAVA_AWT_WM_NONREPARENTING=1
    export NO_AT_BRIDGE=1
    
    # Desativa pipelines gráficos que costumam dar tela branca no XWayland
    java -Dsun.java2d.opengl=false -Dsun.java2d.xrender=false -cp ".:lib/*:src" controller.MainApp
else
    echo "Erro na compilação!"
    exit 1
fi
