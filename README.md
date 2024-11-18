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

### Unit Test
- example to using SpringBootTest
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(loader = MyCustomContextLoader.class)
class GenericApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
```