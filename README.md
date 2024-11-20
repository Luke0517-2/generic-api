# Generic api
## api swagger url
* api url: ``http://localhost:8062/api/generic/v1/swagger-ui/index.html#/``
### ngnix.conf
```conf
        location /api/generic/v1 {
            proxy_pass_request_headers on;
            proxy_set_header Host $host;
            proxy_set_header X-Forwarded-Prefix '/api/generic/v1';

            proxy_pass http://genericApi/api/generic/v1;
        }
		
		
    upstream genericApi {
        server localhost:8062;
    }
``` 

## Api 命名規則
- https://xxxxxx/api/generic/v1/esg/user/login
1. api 用途
2. generic 專用
3. v1 版本
4. esg 模組
5. user 功能列表
6. login 功能
```text
C: Post https://xxxxxx/api/generic/v1/oc64/electricity-usages 
R: Get https://xxxxxx/api/generic/v1/oc64/electricity-usages 
U: Put https://xxxxxx/api/generic/v1/oc64/electricity-usages 
D: Put https://xxxxxx/api/generic/v1/oc64/electricity-usages 
```


## Unit Test
- example to using SpringBootTest
  - KeyPoint 
  1. `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)`
  2. `@ContextConfiguration(loader = MyCustomContextLoader.class)`

```java
package com.iisigroup.generic.module.oc64;

import com.iisigroup.generic.config.MyCustomContextLoader;
import com.iisigroup.generic.module.oc64.service.impl.RolesServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(loader = MyCustomContextLoader.class)
public class RolesServiceImplTest {

    @Autowired
    private RolesServiceImpl rolesService;

    @Test
    void checkNotNull() {
        Assertions.assertNotNull(rolesService);
    }
}
```