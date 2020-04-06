// 1.synchronized关键字+this锁对象(两个不同的线程将会共用一个FooBar实例)
class FooBar {
    // 原因: 两个线程属于同一个实例，虽然两个线程访问的是一个类的不同的普通同步方法，但是两个普通同步方法默认都是以this对象作为同步方法的锁，所以它们会争抢同一把锁（对于同一个实例来讲，两个方法的this对象是同一个）。
    // 结果: 两个线程争抢同一把锁，同一时刻只能有一个线程执行该线程对应的同步方法，然后再使用wait方法和notify方法，使得两个线程交替运行。
    private int n;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                printFoo.run();
                // 唤醒另一个线程
                this.notify();
                // 自己陷入等待
                this.wait();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                printBar.run();
                // 唤醒另一个线程
                this.notify();
                // 防止线程在最后一次打印Bar时睡眠，从而造成死锁。
                if (i != n - 1) {
                    // 自己陷入等待
                    this.wait();
                }
            }
        }
    }
}

// 2.lock+condition
class FooBar {
    private int n;
    // 使用lock代替synchronized关键字
    private Lock lock = new ReentrantLock();
    // 使用condition代替Object的wait方法和notify方法
    private Condition condition = lock.newCondition();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            printFoo.run();
            // 唤醒另一个线程
            condition.signalAll();
            // 自己陷入等待
            condition.await();
            lock.unlock();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            printBar.run();
            // 唤醒另一个线程
            condition.signalAll();
            // 防止线程在最后一次打印Bar时睡眠，从而造成死锁。
            if (i != n - 1) {
                // 自己陷入等待
                condition.await();
            }
            lock.unlock();
        }
    }
}

// 3.信号量(Semaphore)
// 两个线程第一次执行时，因为bar信号量的计数器初始值为0，所以需要等foo线程中释放了bar信号量，bar线程才能开始执行。而foo信号量在获取了一个许可后，其计数器初始值也变为0，所以foo线程在下一次循环开始时需要等bar线程中释放了foo信号量，foo线程才能继续执行。
class FooBar {
    private int n;
    private Semaphore foo = new Semaphore(1);
    private Semaphore bar = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // 获取foo信号量的一个许可
            foo.acquire();
            printFoo.run();
            // 释放bar信号量的一个许可
            bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // 获取bar信号量的一个许可
            bar.acquire();
            printBar.run();
            // 释放foo信号量的一个许可
            foo.release();
        }
    }
}

// 4.CountDownLatch+CyclicBarrier
class FooBar {
    private int n;
    // CyclicBarrier用于保证任务按组循环执行
    private CyclicBarrier barrier = new CyclicBarrier(2);
    // CounDownLatch用于保证一个循环内线程执行的先后顺序
    private CountDownLatch latch = new CountDownLatch(1);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try{
                printFoo.run();
                // 触发bar线程执行
                latch.countDown();
                // 等待bar线程执行完成
                barrier.await();
            } catch(Exception e) {}
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try {
                // 等待被触发
                latch.await();
                printBar.run();
                latch = new CountDownLatch(1);
                // 触发foo线程和bar线程继续执行(进行下一次循环)
                barrier.await();
            } catch (Exception e) {}
        }
    }
}