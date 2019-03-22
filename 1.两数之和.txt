1.暴力解法

int* twoSum(int* nums, int numsSize, int target) {
    int* result = (int*)malloc(sizeof(int)*2);

    for (int i = 0; i < numsSize-1; i++) {
        for (int j = i+1; j < numsSize; j++) {
            if (nums[i] + nums[j] == target) {
                result[0] = i;
                result[1] = j;
                return result;
            }
        }
    }
        
    return NULL;
}


2.两遍哈希表

int* twoSum(int* nums, int numsSize, int target) {   
    int* result = (int*)malloc(sizeof(int)*2);
    int min = INT_MAX, max = INT_MIN;
    
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] < min)
            min = nums[i];
        if (nums[i] > max)
            max = nums[i];
    }
    
    // 创建哈希表。
    int len = max-min+1;
    int* hash = malloc(sizeof(int)*len);
    
    // 初始化哈希表。
    for (int i = 0; i < len; i++)
        hash[i] = -1;
    // 迭代1 —— 将每个元素的值和它的索引添加到表中。
    for (int i = 0; i < numsSize; i++)
        hash[nums[i]-min] = i;
    // 迭代2 —— 检查每个元素所对应的目标元素(target - nums[i])是否存在于表中。
    for (int i = 0; i < numsSize; i++) {
        int diff = target-nums[i]-min;
        
        if (diff >= 0 && diff < len && hash[diff] >= 0 && i != hash[diff]) {
            result[0] = i;
            result[1] = hash[diff];
            return result;
        }
    }
    
    return NULL;
}


3.一遍哈希表

int* twoSum(int* nums, int numsSize, int target) {   
    int* result = (int*)malloc(sizeof(int)*2);
    int min = INT_MAX, max = INT_MIN;
    
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] < min)
            min = nums[i];
        if (nums[i] > max)
            max = nums[i];
    }
    
    // 创建哈希表。
    int len = max-min+1;
    int* hash = malloc(sizeof(int)*len);
    
    // 初始化哈希表。
    for (int i = 0; i < len; i++)
        hash[i] = -1;

    // 迭代1和迭代2一起进行，即边将每个元素的值和它的索引添加到表中，边检查每个元素所对应的目标元素(target-nums[i])是否存在于表中。
    for (int i = 0; i < numsSize; i++) {
        int diff = target-nums[i]-min;
        
        if (diff >= 0 && diff < len && hash[diff] >= 0 && i != hash[diff]) {
            result[0] = i;
            result[1] = hash[diff];
            return result;
        }
        else
            hash[nums[i]-min] = i;
    }
    
    return NULL;
}


4.uthash

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
int* twoSum(int* nums, int numsSize, int target) {    
    struct hash {
        int key;
        int value;
        // makes this structure hashable.
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    int* result = malloc(sizeof(int)*2);
    
    for (int i = 0; i < numsSize; i++) {
        struct hash *h;
        // 查找键值对。
        HASH_FIND_INT(hashTable, nums+i, h);
        
        if (h) {
            result[0] = i;
            result[1] = h->value;
            return result;
        } 
        else {
            h = malloc(sizeof(struct hash));
            h->key = target-nums[i];
            h->value = i;
            // 添加键值对。
            HASH_ADD_INT(hashTable, key, h);
        }
    }
    
    return result;
}