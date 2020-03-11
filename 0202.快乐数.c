// 1.数组
bool isHappy(int n) {
    int hash[500] = {0};
    while (n != 1) {
        int sum = 0;
        // 求每个位置上的数字的平方和
        while (n > 0) {
            int d = n % 10;
            n /= 10;
            sum += d * d;
        }
        // 判断是否陷入死循环
        if (hash[sum] == 0) {
            hash[sum] = 1;
            n = sum;
        }
        else {
            return false;
        }
    }
    return true;
}

// 2.哈希表(uthash)
// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
struct hash {
    int value;
    UT_hash_handle hh; 
};

bool isHappy(int n) {
    struct hash *hashTable = NULL;
    while (n != 1) {
        int sum = 0;
        // 求每个位置上的数字的平方和
        while (n > 0) {
            int d = n % 10;
            n /= 10;
            sum += d * d;
        }
        struct hash *h;
        HASH_FIND_INT(hashTable, &sum, h);
        // 判断是否陷入死循环
        if (!h) {
            h = malloc(sizeof(struct hash));
            h->value = sum;
            HASH_ADD_INT(hashTable, value, h);
            n = sum;
        } 
        else {
            return false;
        }
    }
    return true;
}

// 3.数学
bool isHappy(int n) {
    while (n != 1) {
        int sum = 0;
        // 判断是否陷入死循环
        if (n == 4) {
            return false;
        }
        // 求每个位置上的数字的平方和
        while (n > 0) {
            int d = n % 10;
            n /= 10;
            sum += d * d;
        }
        n = sum;
    }
    return true;
}

// 4.数学方法优化
// 若n<=6则跳出循环，此时若n==1，则是快乐数，若n==2/3/4/5/6，则不是快乐数(一个数在循环的过程中，若循环到非快乐数，则这个数不可能为快乐数)。
bool isHappy(int n) {
    while (n > 6) {
        int sum = 0;
        // 求每个位置上的数字的平方和
        while (n > 0) {
            int d = n % 10;
            n /= 10;
            sum += d * d;
        }        
        n = sum;
    }
    return n == 1;
}