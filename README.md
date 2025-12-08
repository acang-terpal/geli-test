# spring-boot-data-jpa-geli-test
technologi stack:
- database sqlite
- cache (ehcache) for cart purpose
- jpa persistent with JPQL
- MVC design with router class
- using TransactionalPayment class with synchronized methode to make sure, deduct stock not enter in race condition

![Database ERD](img/ERDDiagram.png "Database ERD")
<br>has 4 table :
- stock, this is stock category
- colour, for colour varian category
- size, for size varian category
- item, it transaction data

url path
- localhost:8080/geli/ControllerGeli/getItem?page=0&size=5 
  <br>for get all item
  ![Get All Item](img/getAllItem.png "Get All Item")
- localhost:8080/geli/ControllerGeli/getItemById?itemId=4
  <br>for get item by id
  ![Get Item by Id](img/getItemById.png "Get All Item")
- localhost:8080/geli/ControllerGeli/getColour?page=0&size=5
  <br>for get all colour
  ![Get all coluur](img/getAllColour.png "Get All colour")
- localhost:8080/geli/ControllerGeli/getColourById?colourId=4
  <br>for get all colour
  ![Get all coluur](img/getColourById.png "Get All colour")
- localhost:8080/geli/ControllerGeli/getSize?page=0&size=2
  <br>for get all size
  ![Get all size](img/getAllSize.png "Get All size")
- localhost:8080/geli/ControllerGeli/getCart
  <br>for get all cart
  ![Get all size](img/getAllCart.png "Get All size")
- localhost:8080/geli/ControllerGeli/getSizeById?sizeId=5
  <br>for get size by id
  ![Get all size](img/getSizeById.png "Get All size") 
- localhost:8080/geli/ControllerGeli/getStock?page=0&size=5
  <br>for get all stock
  ![Get all size](img/getAllStock.png "Get All size")
- localhost:8080/geli/ControllerGeli/getStockById?stockId=5
  <br>for get all stock by id
  ![Get all size](img/getStockById.png "Get All size")
- localhost:8080/geli/ControllerGeli/createItem
  <br>for create new item
  ![Get all size](img/createItem.png "Get All size")
- localhost:8080/geli/ControllerGeli/deleteItem
  <br>for delete item
- localhost:8080/geli/ControllerGeli/deleteColour
  <br>for delete colour category
- localhost:8080/geli/ControllerGeli/deleteSize
  <br>for delete size category
- localhost:8080/geli/ControllerGeli/updateItem
  <br>for update item
- localhost:8080/geli/ControllerGeli/createColour
  <br>for update item
- localhost:8080/geli/ControllerGeli/createSize
  <br>for create size category
- localhost:8080/geli/ControllerGeli/addCart
  <br>for add cart
- localhost:8080/geli/ControllerGeli/payCart
  <br>for payment cart

how to configure
- make sure workdir on application.properties is set with jar file path
- change http.port if needed

how to compile
- run command "mvn clean package"
- result on folder target with name GELI-TEST-1.0-SNAPSHOT.jar

how to run
- java -jar GELI-TEST-1.0-SNAPSHOT.jar

note: i add postman script (Collection 2.1 export version) for testing purpose, file name is "GELI TEST.postman_collection.json"