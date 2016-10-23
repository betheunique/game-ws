# game-ws

## Spring based webservice. As complexity increases very moduler webservice can be made. 
- Spring 3.0.5  (DI)
- Jedis.
- Hibernet is used for persistant data store (MySQL) communication. 
- Jersey.
- Jackson JSON serialization and de-serialization (Commented in the service but its powerful)
- Embedded Jetty (war deployment).

## Volatile storage is used for temporary storage. 
- HashMap for sessionId and its corresponding state is stored in it. 

## Redis is used for user state cache. 
- Jedis for redis client. - It can be persistance stotrage but in this microservice i used as fault tolerance storage. If server breaks before commiting changes to MySQL, state can be retrived form redis and again stored into MYSQL  

## MySQL can be used for persistance data storage.  

## Synchronization can be implemented for replicating data from redis to MySQl.
- User login can be implemented

## Create Databse
- add table g_session_state
- (DROP TABLE IF EXISTS `g_session_state`; CREATE TABLE `t_user_rules` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `session_id` VARCHAR(200) NOT NULL,
  `total_value` VARCHAR(200) NOT NULL
  PRIMARY KEY (`id`),) ENGINE=InnoDB DEFAULT CHARSET=utf8; )
- if not
- *genericDBService @Autowired gives failure comment autowiring and create object of GenericDBService in SessionIDAccess

## Command to package and deployment.
- mvn clean package
- mvn jetty:deploy-war
