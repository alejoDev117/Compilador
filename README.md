# Proyecto de Compilador en Java

Este proyecto es un compilador en Java que traduce código fuente de un lenguaje específico a HTML. A continuación se describe la estructura del proyecto y su contenido.

## Estructura del Proyecto

La estructura de carpetas en la raíz del proyecto es la siguiente:


### /lexer

Esta carpeta contiene los componentes relacionados con el análisis léxico:

- **`Lexer.java`**: Implementa el análisis léxico que convierte el texto de entrada en una secuencia de tokens.
- **`Token.java`**: Define la estructura de un token, que incluye el tipo de token y su valor.
- **`TokenType.java`**: Enumera todos los tipos de tokens que el lexer puede identificar.
- **`README.md`**: Información adicional específica sobre el módulo de análisis léxico.

### /data

Contiene los archivos de entrada y datos necesarios para el análisis:

- **`InputFiles`**: Carpeta para almacenar archivos de ejemplo que serán procesados por el compilador.
- **`README.md`**: Información sobre los archivos de entrada y cómo se deben usar.

### /main

Contiene la lógica principal y los componentes del compilador:

- **`Main.java`**: Punto de entrada del programa. Aquí se inicia el compilador y se gestionan las tareas principales.
- **`Parser.java`**: Implementa el análisis sintáctico que verifica si los tokens siguen las reglas gramaticales del lenguaje.
- **`README.md`**: Información adicional sobre el módulo principal del compilador.

## Cómo Usar el Proyecto

1. **Configura tu entorno**: Asegúrate de tener Java JDK instalado y configurado en tu máquina.

2. **Compila el proyecto**: Navega a la raíz del proyecto y compila el código fuente usando `javac`.

   ```sh
   javac -d bin src/**/*.java
