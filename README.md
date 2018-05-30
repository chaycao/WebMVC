# WebMVC
用Java实现的Web MVC框架

## 目前支持功能：
### 1. 路由 
- @Controller  
标注处理器
- @RequestMapping  
标注路由方法    

### 2. 路由方法传参 
使用@RequestParam注解，为路由方法传参
- 支持请求方式：
    - GET
    - POST
        - x-www-form-urlencoded
        - form-data
- 支持参数
    - 八种基本类型
    - String
    - 文件 @RequestPart
    
### 3. 视图
- JSP

### 4. 添加额外Servlet、Filter   
实现WebApplicationInitializer接口，通过代码动态添加


## 正在开发功能：
- 支持以JSON为媒体的RESTFul风格请求及返回视图
- 支持参数List
