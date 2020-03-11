// 1.synchronized关键字+this锁对象
class BoundedBlockingQueue {
    // 用线程安全的集合实现一个阻塞队列
    private LinkedList<Integer> list = new LinkedList<>();
    // 存储当前队列元素个数
    AtomicInteger size = new AtomicInteger(0);
    // 存储队列长度上限
    private volatile int capacity;

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }
    
    public void enqueue(int element) throws InterruptedException {
        synchronized (this) {
            // 如果队列满，调用线程被阻塞直到队列非满。
            while (size.get() >= capacity) {
                // 线程阻塞时会释放锁
                this.wait();
            }
            // 在队首增加一个element
            list.addFirst(element);
            // 当前队列元素个数加一
            size.incrementAndGet();
            // 通知消费者线程可以继续消费了
            this.notify();
        }
    }
    
    public int dequeue() throws InterruptedException {
        synchronized (this) {
            // 如果队列空，调用线程被阻塞直到队列非空。
            while (size.get() == 0) {
                // 线程阻塞时会释放锁
                this.wait();
            }
            // 返回队尾元素并从队列中将其删除
            int value = list.getLast();
            list.removeLast();
            // 当前队列元素个数减一
            size.decrementAndGet();
            // 通知生产者线程可以继续生产了
            this.notify();
            return value;
        }
    }
    
    public int size() {
        return size.get();
    }
}

// 2.lock+condition
class BoundedBlockingQueue {
    // 用线程安全的集合实现一个阻塞队列
    private LinkedList<Integer> list = new LinkedList<>();
    // 存储当前队列元素个数
    AtomicInteger size = new AtomicInteger(0);
    // 存储队列长度上限
    private volatile int capacity;
    // 可重入锁
    private Lock lock = new ReentrantLock();
    Condition procuder = lock.newCondition();
    Condition consumer = lock.newCondition();

    public BoundedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }
    
    public void enqueue(int element) throws InterruptedException {
        try {
            lock.lock();
            // 如果队列满，调用线程被阻塞直到队列非满。
            while (size.get() >= capacity) {
                // 线程阻塞时会释放锁
                procuder.await();
            }
            // 在队首增加一个element
            list.addFirst(element);
            // 当前队列元素个数加一
            size.incrementAndGet();
            // 通知消费者线程可以继续消费了
            consumer.signal();
        } finally {
            lock.unlock();
        }
    }
    
    public int dequeue() throws InterruptedException {
        try {
            lock.lock();
            // 如果队列空，调用线程被阻塞直到队列非空。
            while (size.get() == 0) {
                // 线程阻塞时会释放锁
                consumer.await();
            }
            // 返回队尾元素并从队列中将其删除
            int value = list.getLast();
            list.removeLast();
            // 当前队列元素个数减一
            size.decrementAndGet();
            // 通知生产者线程可以继续生产了
            procuder.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }
    
    public int size() {
        return size.get();
    }
}