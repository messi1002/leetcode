1.暴力解法

bool containsDuplicate(int* nums, int numsSize) {
    for (int i = 0; i < numsSize; i++) {
        for (int j = i+1; j < numsSize; j++) {
            if (nums[i] == nums[j])
                return true;
        }
    }
    
    return false;
}


2.哈希表

bool containsDuplicate(int* nums, int numsSize) {
    if (numsSize == 0)
        return false;
    
    int min = nums[0], max = nums[0], len;

    // 确定哈希表范围。
    for (int i = 1; i < numsSize; i++) {
        if (nums[i] > max)
            max = nums[i];
        if (nums[i] < min)
            min = nums[i];
    }
    
    // 哈希表。
    len = max-min+1;
    int* hash = malloc(sizeof(int)*len);
    
    // 初始化哈希表。
    for (int i = 0; i < len; i++)
        hash[i] = 0;
    for (int i = 0; i < numsSize; i++) {
        // 判断元素是否已经出现过。
        if (hash[nums[i]-min] == 1)
            return true;
        
        hash[nums[i]-min] = 1;
    }
    
    return false;
}


3.uthash

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
bool containsDuplicate(int* nums, int numsSize) {
    struct hash {
        int value;
        // makes this structure hashable.
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    
    for (int i = 0; i < numsSize; i++) {
        struct hash *h;
        // 查找值。
        HASH_FIND_INT(hashTable, nums+i, h);
        
        if (h)
            return true;
        else {
            h = malloc(sizeof(struct hash));
            h->value = nums[i];
            // 若值不存在则添加。
            HASH_ADD_INT(hashTable, value, h);
        }
    }
    
    return false;
}


4.排序

// 快排函数(升序)。
int compare(const void* a, const void* b) {
    return (*(int*)a)-(*(int*)b);
}

bool containsDuplicate(int* nums, int numsSize) {
    qsort(nums, numsSize, sizeof(int), compare);
    
    for (int i = 1; i < numsSize; i++) {
        if (nums[i] == nums[i-1])
            return true;
    }
    
    return false;
}