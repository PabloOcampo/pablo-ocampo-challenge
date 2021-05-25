# BitTracker

Esta aplicacion se encuentra comprendida por un microservicio desarrollado en Java version 1.8, utilizando SpringBoot como framework principal. Su funcion es obtener cada 10 segundos el valor de la cripto moneda 'Bitcoin' y almacenarlo en una base de datos en memoria.  <br />
Luego de esto, permite las siguientes consultas:

* Consultar su valor en un timestamp especifico.
* Obtener el valor promedio entre dos timestamps, incluyendo el valor maximo y la diferencia porcentual entre dichos valores.

Adicionalmente, se incluyen los servicios CRUD necesarios para el manejo manual de las entidades e informacion almacenada.


### Pre-requisitos 📋

El sistema debe contar con Java 1.8 instalado.


### Ejecucion 🔧

El primer paso es clonar el repositorio mediante el siguiente comando:

```
git clone https://github.com/PabloOcampo/pablo-ocampo-challenge.git
```

A continuacion, se debe posicionar en la carpeta raiz del projecto y ejecutar el siguiente comando:

```
./mvnw spring-boot:run
```

Luego de esto, se iniciara la descarga de las dependencias necesarias y la posterior ejecucion de la aplicacion.

La URL expuesta por defecto es:

```
http://localhost:8081
```
De ser necesario, el puerto utilizado puede ser modificado en la properties "server.port" ubicada en el archivo aplications.yml


## Ejecucion de pruebas unitarias ⚙️

Para ejecutar el conjunto de pruebas unitarias incluido, se debe posicionar en la carpeta raiz del projecto y ejecutar el siguiente comando:

```
./mvnw test
```

Estas pruebas se encargan de probar los diferentes endpoint y recursos que expone este microservicio.


## Frameworks y dependencias utilizadas 🛠️

* Java 8 - Lenguaje de desarrollo
* SpringBoot - Framework de desarrollo
* Maven - Manejador de dependencias
* H2 Database - Base de datos utilizado para la persistencia en memoria
* Apache Commons Math - Libreria utilizada para calculos matematicos


### Recursos expuestos por este microservicio:

Consulta sobre el valor del bitcoin en un timestamp determinado:

* **URL**

```
/api/cryptos/timestamp/{timestamp}
```

* **Method:**

```
GET
```

* **Required URL parameter:**

```
timestamp = Timestamp con formato 'yyyy-MM-dd'T'HH:mm:ss' indicando la fecha y hora a consultar.
```

* **Success Response:**

Se devuelve la informacion de precio disponible para esa fecha y hora:

```
* **Code: 200** <br />
  **Content:**  <br />
{
    "initialTimestamp": "2021-05-20T18:42:25",
    "finalTimestamp": "2021-05-25T23:59:14",
    "avgPriceBetweenTimestamps": 39123.8,
    "maxPrice": 39140.0,
    "percentageDifference": 0.04
}
```

* **Error Response:**

En caso de que no se encuentre informacion disponible para ese timestamp, se obtendra la siguiente respuesta:

```
* **Code: 404 BAD REQUEST** <br />
  **Content:** <br />
{
    "timestamp": "2021-05-25T02:35:18.487+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "No price information available for the given timestamp",
    "path": "/api/cryptos/timestamp/2021-05-24T21:05:33"
}
```

* **Ejemplo de llamado via CURL:**

```
curl --location --request GET 'http://localhost:8081/api/cryptos/timestamp/2021-05-24T21:05:33'
```


Consulta sobre informacion de precios entre un rango de timestamps determinado:

* **URL**

```
/api/cryptos/priceinfo
```

* **Method:**

```
GET
```

* **Required query parameters:**

```
initialTimestamp = Timestamp con formato 'yyyy-MM-dd'T'HH:mm:ss', indica el comienzo del rango de fechas a consultar

finalTimestamp = Timestamp con formato 'yyyy-MM-dd'T'HH:mm:ss', indica el final del rango de fechas a consultar
```

* **Success Response:**

Se devuelve la informacion de precio disponible para ese rango de fechas:

```
* **Code: 200** <br />
  **Content:** <br />
{
    "initialTimestamp": "2021-05-20T18:42:25",
    "finalTimestamp": "2021-05-25T23:59:14",
    "avgPriceBetweenTimestamps": 39123.8,
    "maxPrice": 39140.0,
    "percentageDifference": 0.04
}
```

* **Error Response:**

En caso de que no se encuentre informacion disponible para el rango de fechas requerido, se obtendra la siguiente respuesta:

```
* **Code: 404 BAD REQUEST** <br />
  **Content:** <br />
{
    "timestamp": "2021-05-25T01:24:00.267+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "No price information available for the given timestamp range",
    "path": "/api/cryptos/priceinfo"
}
```

* **Ejemplo de llamado via CURL:**

```
curl --location --request GET 'http://localhost:8081/api/cryptos/priceinfo?initialTimestamp=2021-05-20T18:42:25&finalTimestamp=2021-05-23T23:59:14'
```

## Versionado 📌

V1.0.0 - Version actual.

## Autor ✒️

* **Pablo Ocampo** - *Desarrollo y documentacion*


