# GraphQL server in Java demo application

See the following articles for details:

* [Part I: Basics](https://www.nurkiewicz.com/2019/10/graphql-server-in-java-part-i-basics.html)
* [Part II: Understanding Resolvers](https://www.nurkiewicz.com/2019/10/graphql-server-in-java-part-ii.html)
* [Part III: Improving concurrency](https://www.nurkiewicz.com/2020/03/graphql-server-in-java-part-iii.html)


编译
```
gradle build
```

## Running zipkin locally

    docker run -d -p 9411:9411 openzipkin/zipkin --name zipkin
  
## 调用graphql服务  
    
### 请求1 - 查询所有数据

```
request_body.txt
```

### 请求2 - 根据ID查询

```
request_body2.txt
```

### 更多例子

```
https://github.com/graphql-java-kickstart/samples/tree/master/spring-boot-webflux
```

## 参考文档

* https://github.com/nurkiewicz/graphql-server-demo
* https://nurkiewicz.com/2020/03/graphql-server-in-java-part-iii.html
* https://learning.postman.com/docs/sending-requests/supported-api-frameworks/graphql/
* https://piotrminkowski.com/2020/07/31/an-advanced-guide-to-graphql-with-spring-boot/