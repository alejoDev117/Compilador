# Proyecto de Compilador en Java

Este proyecto es un compilador en Java que traduce código fuente de un lenguaje específico a HTML. A continuación se describe la estructura del proyecto y su contenido.

## Estructura del Proyecto

La estructura de carpetas en la raíz del proyecto es la siguiente:


### /lexer

Esta carpeta contiene los componentes relacionados con el análisis léxico:

- **`Lexer.java`**: Implementa el análisis léxico que convierte el texto de entrada en una secuencia de tokens.
- **`Token.java`**: Define la estructura de un token, que incluye el tipo de token y su valor.
- **`TokenType.java`**: Enumera todos los tipos de tokens que el lexer puede identificar.

### /data

Contiene los archivos de entrada y datos necesarios para el análisis:

- **`InputFiles`**: Carpeta para almacenar archivos de ejemplo que serán procesados por el compilador.

### /main

Contiene la lógica principal y los componentes del compilador:

- **`Main.java`**: Punto de entrada del programa. Aquí se inicia el compilador y se gestionan las tareas principales.
- **`Parser.java`**: Implementa el análisis sintáctico que verifica si los tokens siguen las reglas gramaticales del lenguaje.

## Contribuir

Si deseas contribuir al proyecto, por favor, realiza un fork del repositorio y envía un pull request con tus cambios. Asegúrate de seguir las directrices de contribución incluidas en la documentación.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para obtener más detalles.

