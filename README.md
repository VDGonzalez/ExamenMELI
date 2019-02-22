# ExamenMELI

Autor: *Victor Gonzalez*.

### Descripción:

**Examen Mercadolibre.**

El proyecto fué desarrollado en lenguaje Java. Utilizando el framework Spring Boot, con Spring Initializr. Y para la base de datos se eligió la tecnología MySQL.

### Requisitos:

- JDK 8u181
- Apache Maven 3.6.0
- MySQL 5.5.27

### Instrucciones de cómo ejecutar la API:

1. **Crear la base de datos:**

    * Utilizando el script que se encuentra en el path *"/src/main/resources/scriptsSQL/createDB.sql"*.

    * O en cualquier herramienta de administración MySQL ejecutar el comando: 
      ```sql
      CREATE DATABASE `mutant-ml`;
      ```

2. **Configurar la conexión a la base de datos:**

    Editar las líneas correspondientes a la conexión con la base de datos en el archivo *"application.properties"*, que se encuentra en el path: *"/src/main/resources/application.properties"*. Utilizando los datos de nuestro servidor local.
    
    Líneas:
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/mutant-ml?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    spring.datasource.username=usuario
    spring.datasource.password=contraseña
    ```
3. **Compilar el proyecto para producción:**

    Situarse sobre el directorio principal del proyecto y ejecutar el siguiente comando:
    ```
    mvn clean install -DskipTests
    ```
4. **Ejecutar la API:**

    Situarse dentro del directorio *"/target"* dónde se generó el archivo *"mutant-0.0.1-SNAPSHOT.jar"*, y ejecutar:
    ```
    java -jar mutant-0.0.1-SNAPSHOT.jar
    ```
### Pruebas:

Se puede probar la API con alguna herramienta como POSTMAN o API TEST. Las URLs por defecto son:

```
-------------------------------------------------------------------------------

Verbo:    POST

URL:      http://localhost:5000/mutant/

Header:   Content-Type    application/json
        
Body (JSON):

        {
          "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
        }

-------------------------------------------------------------------------------

Verbo:    GET

URL:      http://localhost:5000/stats

-------------------------------------------------------------------------------
```

Se agregó un servicio auxiliar para borrar todos los elementos de la base de datos:

```
-------------------------------------------------------------------------------

Verbo:    DELETE

URL:      http://localhost:5000/deleteall

-------------------------------------------------------------------------------
```
