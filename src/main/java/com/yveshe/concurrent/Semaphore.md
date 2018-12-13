# 1.需求
10个人去火车站买票,只有2个售票窗口,当其中2个人中有一个人买完票,接下等待的人就可以买票.

---

# 2.拆分过程
10个人相当于10个线程,2个窗口就是2个资源,最终结果为同一时间,允许的并发量为2的程序.

---

# 3.具体实现见代码
使用Semaphore组件实现该限流程序,详情见代码实现.

---

# 4.总结
Semaphore适用在一些敏感的资源的操作,比如数据库连接和打印机连接之类的操作,也可以适用在一些限流的操作中.

---

# Semaphore介绍

---

# 主要方法
## 构造方法
- Semaphore(int permits) <br>
          创建具有给定的许可数和默认的非公平设置的 Semaphore。 
- Semaphore(int permits, boolean fair) <br>
          创建具有给定的许可数和**给定的公平设置**的 Semaphore。 


## 常用方法
- void acquire()  <br>
          从此信号量**获取一个许可**，在提供一个许可前一直将线程阻塞，否则线程被中断。 
-  void acquire(int permits)  <br>
          从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞，或者线程已被中断。 
-  void acquireUninterruptibly() <br>
          从此信号量中获取许可，在有可用的许可前将其阻塞。 
-  void acquireUninterruptibly(int permits) <br>
          从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞。 
-  boolean tryAcquire() <br>
          仅在调用时此信号量存在一个可用许可，才从信号量获取许可。 
-  boolean tryAcquire(int permits) <br>
          仅在调用时此信号量中有给定数目的许可时，才从此信号量中获取这些许可。 
-  boolean tryAcquire(int permits, long timeout, TimeUnit unit) <br>
          如果在给定的等待时间内此信号量有可用的所有许可，并且当前线程未被中断，则从此信号量获取给定数目的许可。 
-  boolean tryAcquire(long timeout, TimeUnit unit) <br>
          如果在给定的等待时间内，此信号量有可用的许可并且当前线程未被中断，则从此信号量获取一个许可。 

---

-  int availablePermits() <br>
          返回此信号量中当前可用的许可数。 
-  int drainPermits() <br>
          获取并返回立即可用的所有许可。 
- protected  Collection<Thread> getQueuedThreads() <br>
          返回一个 collection，包含可能等待获取的线程。 
-  int getQueueLength() <br>
          返回正在等待获取的线程的估计数目。 
-  boolean hasQueuedThreads() <br>
          查询是否有线程正在等待获取。 
-  boolean isFair() <br>
          如果此信号量的公平设置为 true，则返回 true。 
- protected  void reducePermits(int reduction) <br>
          根据指定的缩减量减小可用许可的数目。 
-  String toString()  <br>
          返回标识此信号量的字符串，以及信号量的状态。 

---

-  void release() <br>
          释放一个许可，将其返回给信号量。 
-  void release(int permits) <br>
          释放给定数目的许可，将其返回到信号量。 