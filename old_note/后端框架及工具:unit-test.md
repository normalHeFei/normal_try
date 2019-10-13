mockito
vertify() 用来验证某个 关心的方法的调用情况
语法： vertify(mock, VerificationMode).someMethod("some arg") ,方法参数可以用ArgumentMatchers 的静态方法指定
例子： vertify(mockObj,atLease(1)).methodOne(eq("hello"))
表示 验证 mockObj 的 methodOne 方法 传参为 “hello” 的情况下最少被调用过1次