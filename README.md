# api-meli-challenge

## Challenge 1:

/desafio1java/AppMercadoLibre.java

## Challengue 2, Run Project:

Main File: ApiMeliApplication.java | path: /src/main/java/api/meli/apimeli/ApiMeliApplication.java

## routes:

http://localhost:8080/coupons | METHOD: POST

## body:

se debe incluir un json con los IDs(itemIds) de los items que querramos incluir(los que se muestran abajo son simplemente un ejemplo) y un presupuesto total que estemos dispuestos a gastar(Amount)

### json example:

{
"itemIds": [
"MLA1",
"MLA2",
"MLA3",
"MLA4"
],
"amount": 50000
}

esto devolvera de la mayor cantidad de items, respecto a los ingresados, que podamos comprar con el presupuesto estipulado

## response example:

[
"MLA1",
"MLA2",
"MLA3",
"MLA4"
]

## Testing:

Code coverage > 80%

verificacion con Jacoco

EN PROGRESO

## Herramienta para verificar el funcionamiento de la API:

Postman


## Heroku link:

https://api-meli-challenge-ayr.herokuapp.com/coupons
