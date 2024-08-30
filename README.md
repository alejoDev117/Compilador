# Proyecto de Compilador en Java

Este proyecto es un compilador en Java que traduce código fuente de un lenguaje específico a HTML. A continuación se describe la estructura del proyecto y su contenido.
![Flujo de funcionamiento del compilador](src/data/Flujo.jpg)
## Estructura del Proyecto

La estructura de carpetas en la raíz del proyecto es la siguiente:
![Diagrama de paquetes](src/data/diagrama_paquetes.jpg)

### /lexer

Esta carpeta contiene los componentes relacionados con el análisis léxico:

- **`Lexer.java`**: Implementa el análisis léxico que convierte el texto de entrada en una secuencia de tokens.
- **`Token.java`**: Define la estructura de un token, que incluye el tipo de token y su valor.
- **`TokenType.java`**: Enumera todos los tipos de tokens que el lexer puede identificar.

### /data

Contiene los archivos de entrada y datos necesarios para el análisis:

- **`InputFiles`**: Carpeta para almacenar archivos de ejemplo que serán procesados por el compilador.

### /main

Contiene la lógica principal del compilador:

- **`Main.java`**: Punto de entrada del programa. Aquí se inicia el compilador y se gestionan las tareas principales.

