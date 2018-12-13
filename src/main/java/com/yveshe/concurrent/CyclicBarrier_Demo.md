# 1.需求
某公司周末组织聚餐吃饭,首先各自从家出发,然后在聚餐地点集合.
如果人员全部到齐后,就可以开始一起吃东西.
如果人员没有到齐,先到的人只能等待(阻塞),直到所有人都到齐后才能开始吃饭.
另外: 人员全部到齐后,希望马上拍照留念.

---

# 2.拆分过程
公司有N个人,对应N个线程,直到N个线程都执行到安全点时,然后N个线程一起执行.
(由于N个线程都会执行安全点后的内容,完全可以控制只让其中一个线程执行即可)

---

# 3.具体实现见代码
使用CyclicBarrier组件实现该限流程序,详情见代码实现.

---

# 4.总结
CyclicBarrier(可循环栅栏,可循环障碍物),常见的场景为多个线程计算数据,最后合并计算结果;也适用于银行的"对账"计算

---

# CyclicBarrier介绍
 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier (循环栅栏)。 
 
 CyclicBarrier 支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），该命令只在每个屏障点运行一次。若在继续所有参与线程之前更新共享状态，此屏障操作 很有用。
 
# CyclicBarrier同步失败策略
对于失败的同步尝试，CyclicBarrier 使用了一种要么全部要么全不 (all-or-none) 的破坏模式：如果因为中断、失败或者超时等原因，导致线程过早地离开了屏障点，那么在该屏障点等待的其他所有线程也将通过 BrokenBarrierException（如果它们几乎同时被中断，则用 InterruptedException）以反常的方式离开。 

内存一致性效果：线程中调用 await() 之前的操作 happen-before 那些是屏障操作的一部份的操作，后者依次 happen-before 紧跟在从另一个线程中对应 await() 成功返回的操作。

---

# 主要方法
## 构造方法
- CyclicBarrier(int parties) <br>
创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，但它不会在启动 barrier 时执行预定义的操作。 
- CyclicBarrier(int parties, Runnable barrierAction) <br>
创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，并在**启动 barrier 时执行给定的屏障操作**，该操作由最后一个进入 barrier 的线程执行。 

**第二参数Runnable barrierAction任务为在所有任务都到达屏蔽点时立即执行的任务,然后接着执行屏蔽点后的任务**

## 常用方法
- int await() <br>
          在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。 
-  int await(long timeout, TimeUnit unit) <br>
          在所有参与者都已经在此屏障上调用 await 方法之前将一直等待,或者超出了指定的等待时间。 
-  int getNumberWaiting() <br>
          返回当前在屏障处等待的参与者数目。 
-  int getParties() <br>
          返回要求启动此 barrier 的参与者数目。 
-  boolean isBroken() <br>
          查询此屏障是否处于损坏状态。 
-  void reset() <br>
          将屏障重置为其初始状态。 

