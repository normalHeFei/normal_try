dubbo
1.1. dubbo协议具体干了什么事情，主要都有哪些协议
Protocol 接口主要实现了两个方法，a. 暴露Exporter(对应producter)，b.引用 一个Invoker(对应consumer)
暴露一个Exporter 其实就是 根据协议服务端通过netty 绑定一个socket端口接收请求，export 过程最后落到NettyServer 类
主要包含 dubbo 协议

1.2. 怎么理解Invoker
invoker 是一个运行时的概念，无论是consumer端还是provider端都可以在需要的时候通过Proxy 来获得具体的调用对象。

1.3 配置
dubbo 的所有配置都集中在url 这个类中。也标识协议本身

1.3. 什么是存根对象？
存根对象其实是调用客户端 对远程真实代理对象的再封装，可以添加增强逻辑。
参考

todo list：

dubbo调用，异常错误如何处理，包含事务的处理
