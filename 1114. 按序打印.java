// 1.使用一个原子变量控制，把并发调用变成按顺序调用。
class Foo {
    private AtomicInteger n = new AtomicInteger(0);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        n.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // 等待第一个线程执行完毕
        while (n.get() != 1) {
        }
        printSecond.run();
        n.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        // 等待第二个线程执行完毕
        while (n.get() != 2) {
        }
        printThird.run();
    }
}

// 2.volatile关键字
class Foo {
    private volatile int flag = 0;
    
    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        flag = 1;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // 等待第一个线程执行完毕
        while (flag != 1) {}
        printSecond.run();
        flag = 2;
    }

    public void third(Runnable printThird) throws InterruptedException {
        // 等待第二个线程执行完毕
        while (flag != 2) {}
        printThird.run();
    }
}

// 3.信号量(Semaphore)
// 三个线程第一次执行时，因为flag1信号量和flag2信号量的计数器初始值都为0，所以需要等first线程中释放了flag1信号量，second线程才能开始执行，需要等second线程中释放了flag2信号量，third线程才能开始执行。
class Foo {
    private Semaphore flag1 = new Semaphore(0);
    private Semaphore flag2 = new Semaphore(0);
    
    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        // 释放flag1信号量的一个许可
        flag1.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // 获取flag1信号量的一个许可
        flag1.acquire();
        printSecond.run();
        // 释放flag2信号量的一个许可
        flag2.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        // 获取flag2信号量的一个许可
        flag2.acquire();
        printThird.run();
    }
}

// 4.CountDownLatch
// CountDownLatch用于控制三个线程执行的先后顺序
class Foo {
    private CountDownLatch latch1 = new CountDownLatch(1);
    private CountDownLatch latch2 = new CountDownLatch(1);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        // 触发second线程执行
        latch1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // 等待被触发
        latch1.await();
        printSecond.run();
        // 触发third线程执行
        latch2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        // 等待被触发
        latch2.await();
        printThird.run();
    }
}

// 5.CyclicBarrier
class Foo {
    private CyclicBarrier barrier1 = new CyclicBarrier(2);
    private CyclicBarrier barrier2 = new CyclicBarrier(2);

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        try {
            printFirst.run();
            barrier1.await();
        } catch (BrokenBarrierException e) {}
    }

    public void second(Runnable printSecond) throws InterruptedException {
        try {
            // 等待fisrt线程执行完printFirst.run();
            barrier1.await();
            printSecond.run();
            barrier2.await();
        } catch (BrokenBarrierException e) {}   
    }

    public void third(Runnable printThird) throws InterruptedException {
        try {
            // 等待second线程执行完printSecond.run();
            barrier2.await();
            printThird.run();
        } catch (BrokenBarrierException e) {}  
    }
}