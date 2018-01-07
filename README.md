## Tiny RPC

tinyrpc 是由动态代理实现的最简单的 RPC 框架，通过 HTTP 进行远程调用，数据通过 JSON 格式传输。客户端调用支持泛型参数和返回结果。

## 使用

### 服务端

新建 client

```java

@RequestRoute(url = "/user")
public interface UserClient {
    @RequestRoute(url = "/addUser")
    Response<Long> addUser(AddUserRequest param);
}

```

client 是提供给客户端调用，客户端定义注意三点

- 必须为接口
- 接口和方法添加注解`RequestRoute`，value 为访路径
- 参数封装为实体，返回值为 Response<>，泛型的实际类型为方法返回类型

controller

```java

@Resource
private UserService userService;

@RequestMapping("/addUser")
public Response<Long> addUser(@RequestBody AddUserRequest param) {
    LOGGER.info("request body: {}", JsonUtils.object2JSONString(param));
    Response<Long> resp = new Response<>();
    resp.setData(userService.addUser(param.getUsername(), param.getMobile()));
    return resp;
}

```

服务端 Controller 中添加和 client 相同的方法，调用 service 的真正方法。

### 客户端

Spring xml 配置

```xml

<bean id="userClient" class="com.somelogs.factory.ClientFactory" factory-method="create">
    <constructor-arg name="config">
        <bean class="com.somelogs.client.ClientConfig">
            <property name="clientClassName" value="com.coderbike.client.UserClient"/>
            <property name="serverUrl" value="${user.client.url}"/>
        </bean>
    </constructor-arg>
</bean>

```

客户端通过工厂的方式注入，配置参数有：

- client 类的全限定命名
- client 服务端地址

Controller 调用

```java

@Resource
private UserClient userClient;

@RequestMapping("/addUser")
public Response<Void> addUser() {
    AddUserRequest param = new AddUserRequest();
    param.setUsername("imant");
    param.setMobile("18019921234");
    Response<Long> response = userClient.addUser(param);
    System.out.println(response);
    return new Response<>();
}

```
