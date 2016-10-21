# game-ws

## Spring based webservice. 
- Spring 3.0.5  - Jedis - Hibernet (Not implemented ) can be used for persistant data. 

## Volatile storage is used for temporary storage. 
- HashMap for sessionId and its corresponding state is stored in it. 

## Redis is used for user state cache. 
- Jedis for redis client. - It can be persistance stotrage but in this microservice i used as fault tolerance storage. If server breaks before commiting changes to MySQL, state can be retrived form redis and again stored into MYSQL  

## MySQL can be used for persistance data storage.  

## Synchronization can be implemented for replicating data from redis to MySQl.

## User login can be implemented
