1.滑动窗口+哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
bool containsNearbyAlmostDuplicate(int* nums, int numsSize, int k, int t) {
    struct hash {
        int value;
        // makes this structure hashable.
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    int len = 0;
    
    for (int i = 0; i < numsSize; i++) {
        struct hash *h1, *h2;
        
        // 控制滑动窗口的大小，即哈希表的大小。
        if (len > k) {
            HASH_FIND_INT(hashTable, nums+len-k-1, h1);
            HASH_DEL(hashTable, h1);
        }

        // 查找当前哈希表中是否存在|nums[i]-键值| <= t。
        for (struct hash *s = hashTable; s!= NULL; s = s->hh.next) {
            if (labs((long)nums[i]-(long)s->value) <= t)
                return true;
        }
        
        HASH_FIND_INT(hashTable, nums+i, h2);

        if (!h2) {   
            h2 = malloc(sizeof(struct hash));
            h2->value = nums[i];
            HASH_ADD_INT(hashTable, value, h2);
            len++;
        }
    }
    
    return false;
}


2.暴力解法

bool containsNearbyAlmostDuplicate(int* nums, int numsSize, int k, int t) {
    for (int i = 0; i < numsSize; i++) {
        int limit = i+k >= numsSize? numsSize: i+k+1;
            
        for (int j = i+1; j < limit; j++) {
            // labs()函数:求长整型的绝对值。
            if (labs((long)nums[i]-(long)nums[j]) <= t)
                return true;
        }
    }
    
    return false;
}