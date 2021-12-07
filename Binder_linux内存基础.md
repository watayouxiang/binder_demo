## Binder面试题

> Linux已有进程通信（管道、共享内存、Socket、File）都不能满足安卓系统，所以才要会有Binder机制。

### 阿里面试题

- 阿里一问：为什么Android需要采用Binder
- 阿里二问：Binder一次拷贝原理是什么 
- 阿里三问：Binder驱动作用机制
- 阿里四问：Intent传递参数为何有限制，限制多少，Binder驱动在哪限制

### 其他面试题

- 为什么Android要采用Binder做进程通信
- 用户空间和内核空间区别
- 简单说说物理地址和虚拟地址
- Binder如何做到一次拷贝
- 简单讲讲mmap原理
- 内存中的一页是什么，你怎么理解的
- Binder传输数据的大小限制是多少
- AIDL生成java类的细节
- Android APP有多少Binder线程，是固定的么
- bindService启动Service与Binder服务实体的流程
- Java层Binder实体与与BinderProxy是如何实例化及使用的，与Native层的关系是怎样的
- Binder如何找到目标进程

## Binder之Linux内存基础

- 用户空间：用户空间是用户所能访问的空间，App进程跑在用户空间中。
- 内核空间：内核空间是“系统代码”、“驱动代码”、“内核代码”、“内核进程”所在的空间。
- MMU
  - Memory Management Unit，内存管理单元，MMU负责的是虚拟地址和物理地址的转换。
  - 页表：虚拟地址和物理地址的映射表
  - 页的概念：为了CPU的高效执行以及方便的内存管理，每次需要拿一页的代码。这个页，指的是一段连续的存储空间（常见的是4K），也叫做块。
- 程序的局部性原则：进程的代码只有局部在屋里内存中运行。这是因为程序具有局部性原则，所以在某一小段时间段内，只有很少一部分代码会被CPU执行。
- 磁盘高速缓存区：磁盘高速缓存区在次盘中，一般大小在8M～16M，磁盘高速缓存区的速度和内存条相当。
- Binder
  - Binder的优势：减少物理内存的拷贝次数
  - Binder一次最多能拷贝多大数据：1M-8K，ProcessState.cpp中定义了BINDER_VM_SIZE为 (1M-8K)
  - Binder的调用：App1 用copy_from_user()将数据从用户空间拷贝到内核空间。内核空间内有 “内核缓存区” 和 “数据接收缓存区”，它们间存在一对一映射关系，而 “数据接收缓存区” 又与 App2 的用户空间存在映射关系，因此 App2 可以直接拿到 “数据接收缓存区” 中的数据（也就是 App1 的数据）。
  - Binder少拷贝一次发生在客户端还是服务端：服务端
- mmap函数
  - 1、物理内存中开辟4096字节内存
  - 2、将物理内存与磁盘进行对应
  - 3、MMU将mmap开辟的物理地址转换成虚拟地址

## Binder四层源码

>  Binder源码有四层：driver、framework、jni、native

![Binder的四层结构](notes_res/Binder的四层结构.png)
