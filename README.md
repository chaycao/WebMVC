# WebMVC
用Java实现的Web MVC框架

## 目前支持功能：
### 1. 路由 
- @Controller  
标注处理器  
- @RequestMapping(value, method, produces, consumes):  
    - value：路由
    - method：请求方法
    - produces：request中的Accept
    - consumes：request中的Content-Type

### 2. 路由方法传参 
使用@RequestParam注解，为路由方法传参
- 支持请求方式：
    - GET
    - POST
        - x-www-form-urlencoded, 表单数据
        - form-data，文件上传
        - raw, JSON
    - PUT
    - DELETE
    - OPTIONS
    - TRACES
    - HEAD
- 支持参数
    - 八种基本类型
    - String
    - 文件 @RequestPart
    - REST风格的URL参数,如/path/{a}/{b}
    - JSON
    
### 3. 视图
- JSP
- JSON
- 重定向 "Redirect:{path}"

### 4. 添加额外Servlet、Filter   
实现```WebApplicationInitializer```接口，通过代码动态添加


## 正在开发功能：

