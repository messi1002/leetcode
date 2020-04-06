// 1.synchronized关键字+this锁对象(三个不同的线程将会共用一个ZeroEvenOdd实例)
class ZeroEvenOdd {
    private int n;
    private volatile int flag = 0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            synchronized(this) {
                while (flag != 0) {
                    // 自己陷入等待
                    this.wait();
                }
                printNumber.accept(0);
                if (i % 2 != 0) {
                    flag = 1;
                }
                else {
                    flag = 2;
                }
                // 唤醒所有线程
                this.notifyAll();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            synchronized(this) {
                while (flag != 2) {
                    // 自己陷入等待
                    this.wait();
                }
                printNumber.accept(i);
                flag = 0;
                // 唤醒所有线程
                this.notifyAll();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            synchronized(this) {
                while (flag != 1) {
                    // 自己陷入等待
                    this.wait();
                }
                printNumber.accept(i);
                flag = 0;
                // 唤醒所有线程
                this.notifyAll();
            }
        }
    }
}

// 2.lock+condition
class ZeroEvenOdd {
    private int n;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private volatile int flag = 0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            lock.lock();
            while (flag != 0) {
                // 自己陷入等待
                condition.await();
            }
            printNumber.accept(0);
            if (i % 2 != 0) {
                flag = 1;
            }
            else {
                flag = 2;
            }
            // 唤醒所有线程
            condition.signalAll();
            lock.unlock();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            while (flag != 2) {
                // 自己陷入等待
                condition.await();
            }
            printNumber.accept(i);
            flag = 0;
            // 唤醒所有线程
            condition.signalAll();
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            while (flag != 1) {
                // 自己陷入等待
                condition.await();
            }
            printNumber.accept(i);
            flag = 0;
            // 唤醒所有线程
            condition.signalAll();
            lock.unlock();
        }
    }
}

// 3.信号量(Semaphore)
class ZeroEvenOdd {
    private int n;
    private Semaphore zero = new Semaphore(1);    
    private Semaphore even = new Semaphore(0);
    private Semaphore odd = new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            // 获取zero信号量的一个许可
            zero.acquire();
            printNumber.accept(0);
            if (i % 2 != 0) {
                // 释放odd信号量的一个许可
                odd.release();
            }
            else {
                // 释放even信号量的一个许可
                even.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            // 获取even信号量的一个许可
            even.acquire();
            printNumber.accept(i);
            // 释放zero信号量的一个许可
            zero.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            // 获取odd信号量的一个许可
            odd.acquire();
            printNumber.accept(i);
            // 释放zero信号量的一个许可
            zero.release();
        }
    }
}

// 4.volatile
class ZeroEvenOdd {
    private int n;
    private volatile int flag = 0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            while (flag != 0) {
                Thread.sleep(1);
            }
            printNumber.accept(0);
            if (i % 2 != 0) {
                flag = 1;
            }
            else {
                flag = 2;
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while (flag != 2) {
                Thread.sleep(1);
            }
            printNumber.accept(i);
            flag = 0;
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            while (flag != 1) {
                Thread.sleep(1);
            }
            printNumber.accept(i);
            flag = 0;
        }
    }
}