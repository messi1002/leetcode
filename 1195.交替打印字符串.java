// 1.synchronized关键字+this锁对象(四个不同的线程将会共用一个FizzBuzz实例)
class FizzBuzz {
    // 原因: 四个线程属于同一个实例，虽然四个线程访问的是一个类的不同的普通同步方法，但是四个普通同步方法默认都是以this对象作为同步方法的锁，所以它们会争抢同一把锁（对于同一个实例来讲，四个方法的this对象是同一个）。
    // 结果: 四个线程争抢同一把锁，同一时刻只能有一个线程执行该线程对应的同步方法，然后再使用wait方法和notify方法，使得四个线程交替运行。
    private int n;
    private int num = 1;
    
    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        while (num <= n) {    
            synchronized (this) {
                // 是3的倍数且不是5的倍数
                if (num % 3 == 0 && num % 5 != 0) {
                    printFizz.run();
                    num++;
                }
                // 唤醒所有线程
                this.notifyAll();
                // 防止线程在最后一次打印时睡眠，从而造成死锁。
                if (num <= n) {
                    // 自己陷入等待
                    this.wait();
                }
            }
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (num <= n) {    
            synchronized (this) {
                // 是5的倍数且不是3的倍数
                if (num % 5 == 0 && num % 3 != 0) {
                    printBuzz.run();
                    num++;
                }
                // 唤醒所有线程
                this.notifyAll();
                // 防止线程在最后一次打印时睡眠，从而造成死锁。
                if (num <= n) {
                    // 自己陷入等待
                    this.wait();
                }
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
         while (num <= n) {    
            synchronized (this) {
                // 是3的倍数且是5的倍数
                if (num % 3 == 0 && num % 5 == 0) {
                    printFizzBuzz.run();
                    num++;
                }
                // 唤醒所有线程
                this.notifyAll();
                // 防止线程在最后一次打印时睡眠，从而造成死锁。
                if (num <= n) {
                    // 自己陷入等待
                    this.wait();
                }
            }
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        while (num <= n) {    
            synchronized (this) {
                // 不是3的倍数且不是5的倍数
                if (num % 3 != 0 && num % 5 != 0) {
                    printNumber.accept(num);
                    num++;
                }
                // 唤醒所有线程
                this.notifyAll();
                // 防止线程在最后一次打印时睡眠，从而造成死锁。
                if (num <= n) {
                    // 自己陷入等待
                    this.wait();
                }
            }
        }
    }
}


// 2.lock+condition
class FizzBuzz {
    private int n;
    private int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    
    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        while (num <= n) {    
            lock.lock();
            // 是3的倍数且不是5的倍数
            if (num % 3 == 0 && num % 5 != 0) {
                printFizz.run();
                num++;
            }
            // 唤醒所有线程
            condition.signalAll();
            // 防止线程在最后一次打印时睡眠，从而造成死锁。
            if (num <= n) {
                // 自己陷入等待
                condition.await();
            }
            lock.unlock();
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (num <= n) { 
            lock.lock();   
            // 是5的倍数且不是3的倍数
            if (num % 5 == 0 && num % 3 != 0) {
                printBuzz.run();
                num++;
            }
            // 唤醒所有线程
            condition.signalAll();
            // 防止线程在最后一次打印时睡眠，从而造成死锁。
            if (num <= n) {
                // 自己陷入等待
                condition.await();
            }
            lock.unlock();
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
         while (num <= n) {
            lock.lock();    
            // 是3的倍数且是5的倍数
            if (num % 3 == 0 && num % 5 == 0) {
                printFizzBuzz.run();
                num++;
            }
            // 唤醒所有线程
            condition.signalAll();
            // 防止线程在最后一次打印时睡眠，从而造成死锁。
            if (num <= n) {
                // 自己陷入等待
                condition.await();
            }
            lock.unlock();
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        while (num <= n) { 
            lock.lock();   
            // 不是3的倍数且不是5的倍数
            if (num % 3 != 0 && num % 5 != 0) {
                printNumber.accept(num);
                num++;
            }
            // 唤醒所有线程
            condition.signalAll();            
            // 防止线程在最后一次打印时睡眠，从而造成死锁。
            if (num <= n) {
                // 自己陷入等待
                condition.await();
            }
            lock.unlock();
        }
    }
}

// 3.信号量(Semaphore)
// 四个线程第一次执行时，因为只有number信号量的计数器初始值为1，其余都为0，所以number线程先执行，让它不断判断接下来该哪个线程执行，并释放对应线程的信号量，对应线程执行一次循环后，释放number信号量，以便number线程继续判断。
class FizzBuzz {
    private int n;
    private Semaphore fizz = new Semaphore(0);
    private Semaphore buzz = new Semaphore(0);
    private Semaphore fizzbuzz = new Semaphore(0);
    private Semaphore number = new Semaphore(1);

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {     
            if (i % 5 != 0) {
                // 获取fizz信号量的一个许可
                fizz.acquire();
                printFizz.run();
                // 释放number信号量的一个许可
                number.release();
            }             
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {      
            if (i % 3 != 0) {
                // 获取buzz信号量的一个许可
                buzz.acquire();
                printBuzz.run();
                // 释放number信号量的一个许可
                number.release();
            } 
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            // 获取fizzbuzz信号量的一个许可
            fizzbuzz.acquire();
            printFizzBuzz.run();
            // 释放number信号量的一个许可
            number.release();
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            // 获取number信号量的一个许可
            number.acquire();
            if (i % 3 == 0 && i % 5 == 0) {
                // 释放fizzbuzz信号量的一个许可
                fizzbuzz.release();
            }
            else if (i % 3 == 0) {
                // 释放fizz信号量的一个许可
                fizz.release();
            }
            else if (i % 5 == 0) {
                // 释放buzz信号量的一个许可
                buzz.release();
            }
            else {
                printNumber.accept(i);
                // 释放number信号量的一个许可
                number.release();
            }
        }
    }
}

// 4.原子变量
class FizzBuzz {
    private int n;
    private AtomicInteger num = new AtomicInteger(1);

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        while (num.get() <= n) {
            int temp = num.get();
            // 是3的倍数且不是5的倍数
            if (temp % 3 == 0 && temp % 5 != 0) {
                printFizz.run();
                num.getAndIncrement();
            }
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (num.get() <= n) {
            int temp = num.get();
            // 不是3的倍数且是5的倍数
            if (temp % 3 != 0 && temp % 5 == 0) {
                printBuzz.run();
                num.getAndIncrement();
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (num.get() <= n) {
            int temp = num.get();
            // 是3的倍数且是5的倍数
            if (temp % 3 == 0 && temp % 5 == 0) {
                printFizzBuzz.run();
                num.getAndIncrement();
            }
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        while (num.get() <= n) {
            int temp = num.get();
            // 不是3的倍数且不是5的倍数
            if (temp % 3 != 0 && temp % 5 != 0) {
                printNumber.accept(temp);
                num.getAndIncrement();
            }
        }
    }
}

// 5.volatile关键字
// 类比信号量的题解
class FizzBuzz {
    private int n;
    private volatile int flag = 0;

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {   
            // 是3的倍数且不是5的倍数
            if (i % 5 != 0) {
                while (flag != 1) { Thread.sleep(1);}
                printFizz.run();
                // 将控制权交还给number()方法
                flag = 0;
            }
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5) { 
            // 不是3的倍数且是5的倍数
            if (i % 3 != 0) {
                while (flag != 2) { Thread.sleep(1);}
                printBuzz.run();
                // 控制权交还给number()方法
                flag = 0;
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            // 是3的倍数且是5的倍数
            while (flag != 3) { Thread.sleep(1);}
            printFizzBuzz.run();
            // 控制权交还给number()方法
            flag = 0;
        }
    }

    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            while (flag != 0) { Thread.sleep(1);}
            if (i % 3 == 0 && i % 5 == 0) {
                flag = 3;
            }
            else if (i % 5 == 0) {
                flag = 2;
            }
            else if (i % 3 == 0) {
                flag = 1;
            }
            // 不是3的倍数且不是5的倍数
            else {
                printNumber.accept(i);
                flag = 0;
            }
        }
    }
}