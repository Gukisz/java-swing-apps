#!/bin/bash
# Script para compilar e executar o projeto Avaliação Java Swing 01.
# Compatível com Linux/Wayland e gerenciadores de janela tiling (ex: Niri, Sway).

# Obtém o diretório do próprio script
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

echo "Compilando os arquivos Java..."

# Compila com a estrutura MVC
javac -cp "src/controller:src/model:src/view" src/controller/*.java src/model/*.java src/view/*.java

if [ $? -eq 0 ]; then
    echo "Compilação bem-sucedida! Iniciando a aplicação..."
    # Variáveis de compatibilidade para Linux/Wayland e gerenciadores de janela tiling
    export _JAVA_AWT_WM_NONREPARENTING=1
    export NO_AT_BRIDGE=1
    
    # Desativa pipelines gráficos que costumam dar tela branca no XWayland
    java -Dsun.java2d.opengl=false -Dsun.java2d.xrender=false -cp "src" controller.TelaPrincipal
else
    echo "Erro na compilação!"
    exit 1
fi
