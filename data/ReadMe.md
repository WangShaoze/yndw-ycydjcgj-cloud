`MYSQL57`导出的数据`xxzx`开头的库 是单个数据库，`new-init.sql` 是JAVA后端需要的完整数据库：

![image-20241120085027640](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120085027640.png)



我电脑上使用的 consul 工具
![image-20241120085340613](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120085340613.png)



目前遇到的问题:

​		这是我遇到的问题，我给他分配了相应的权限，登录他的账号的时候，能看到所有的功能选项，但是在调用接口在访问的时候却出现了权限不足问题。无法正确调用接口，我找了2天了，也不知道怎么解决。![image-20241120090847782](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120090847782.png)



好多接口都出现相同的问题，

![image-20241120091152720](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120091152720.png)



对应的接口，

![image-20241120091321427](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120091321427.png)

![image-20241120091514646](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120091514646.png)





使用系统中 定义的那个 超级管理员，登录就所有接口都是正常的

![image-20241120091743859](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120091743859.png)

数据能够自动加载出来

![image-20241120091905295](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120091905295.png)



这里的接口也是正常的

![image-20241120092001231](C:\Users\TCB\AppData\Roaming\Typora\typora-user-images\image-20241120092001231.png)



我现在遇到的问题就是这样，麻烦大神给帮我解释一下，这个权限分配，怎样操作才会生效？十分感谢