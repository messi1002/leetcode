1.哈希表

bool isHappy(int n) {
    // 哈希表。
    int hash[500] = {0};
    
    while (n != 1) {
        int sum = 0;
        
        // 各个位数求平方和。
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            sum += d*d;
        }
        
        // 判断是否陷入死循环。
        if (hash[sum] == 0) {
            hash[sum] = 1;
            n = sum;
        }
        else
            return false;
    }
    
    return true;
}


2.uthash

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
bool isHappy(int n) {
    struct hash {
        int value;
        // makes this structure hashable.
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    int sum = 0;
    
    while (n != 1) {
        // 各个位数求平方和。
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            sum += d*d;
        }
        
        struct hash *h;
        // 查找值。
        HASH_FIND_INT(hashTable, &sum, h);
        
        if (h)
            return false;
        else {
            h = malloc(sizeof(struct hash));
            h->value = sum;
            // 若值不存在则添加。
            HASH_ADD_INT(hashTable, value, h);
            n = sum;
            sum = 0;
        }
    }

    return true;
}

 
3.数学方法

bool isHappy(int n) {
    while (n != 1) {
        int sum = 0;

        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            sum += d*d;
        }
        
        // 判断是否进入上述死循环。
        if (sum == 4)
            return false;
         
        n = sum;
    }
    
    return true;
}


4.数学方法优化

已知 2、3、4、5、6 都不是快乐数，1、7是快乐数。

bool isHappy(int n) {
    while (n > 6) {
        int sum = 0;

        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            sum += d*d;
        }
        
        // 若 n <= 6 则跳出循环，此时若 n == 1，则为快乐数，若 n == 2/3/4/5/6，则不是快乐数(即一个数在循环的过程中，若循环到非快乐数，则这个数不可能为快乐数)。
        n = sum;
    }
    
    return n == 1;
}