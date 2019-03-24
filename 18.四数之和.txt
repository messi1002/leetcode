1.排序 + 双指针

// 快排函数(升序)。
int compare(const void* a, const void* b) {
    return (*(int*)a)-(*(int*)b);
}

int** fourSum(int* nums, int numsSize, int target, int* returnSize) {
    int** result = malloc(sizeof(int*)*numsSize*numsSize);
    *returnSize = 0;
    
    qsort(nums, numsSize, sizeof(int), compare);   

    for (int i = 0; i < numsSize-3; i++) {
        if (i > 0 && nums[i] == nums[i-1])
            continue;
        for (int j = i+1; j < numsSize-2; j++) {
            int pos1 = j+1, pos2 = numsSize-1, n = nums[i]+nums[j];
            
            // 去除重复值。
            if (j > i+1 && nums[j] == nums[j-1])
                continue;
            // 双指针首尾碰撞。
            while (pos1 < pos2) {
                if (nums[pos1]+nums[pos2] < target-n)
                    pos1++;
                else if (nums[pos1]+nums[pos2] > target-n)
                    pos2--;
                else {
                    result[*returnSize] = malloc(sizeof(int)*4);    
                    result[*returnSize][0] = nums[i];
                    result[*returnSize][1] = nums[j];
                    result[*returnSize][2] = nums[pos1];
                    result[*returnSize][3] = nums[pos2]; 
                    (*returnSize)++;     
                    pos1++;
                    pos2--;
                
                    // 去除重复值。
                    while (pos1 < numsSize && nums[pos1] == nums[pos1-1])
                        pos1++;
                    while (pos2 >= j+1 && nums[pos2] == nums[pos2+1])
                        pos2--;
                }
            }   
            if (nums[j] > 0 && nums[j] >= target)
                break;
        }
        if (nums[i] > 0 && nums[i] >= target)
            break;
    }
        
    return result;
}


2.排序 + 哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
// 快排函数(升序)。
int compare(const void* a, const void* b) {
    return (*(int*)a)-(*(int*)b);
}

int** fourSum(int* nums, int numsSize, int target, int* returnSize) {
    struct hash {
        int value;
        UT_hash_handle hh; 
    };
    
    int** result = malloc(sizeof(int*)*numsSize*numsSize);
    *returnSize = 0;
    qsort(nums, numsSize, sizeof(int), compare);      

    // 可以转化成 15.三数之和，即a+b+c=target-d。
    for (int i = 0; i < numsSize-3; i++) {
        // 去除重复值。
        if (i > 0 && nums[i] == nums[i-1])
            continue;
        // 可以转化成 1.两数之和，即a+b=target-c-d。
        for (int j = i+1; j < numsSize-2; j++) {
            struct hash *hashTable = NULL;
            
            // 去除重复值。
            if (j > i+1 && nums[j] == nums[j-1])
                continue;
            // 寻找两个数，使其和等于target-c-d。    
            for (int k = j+1; k < numsSize; k++) {
                struct hash *h;
                // 边将nums[k]添加到哈希表中，边检查每个元素所对应的目标元素(target-nums[i]-nums[j]-nums[k])是否存在于表中。
                int diff = target-nums[i]-nums[j]-nums[k];
                HASH_FIND_INT(hashTable, &diff, h);
            
                if (!h) {
                    h = malloc(sizeof(struct hash));
                    h->value = nums[k];
                    HASH_ADD_INT(hashTable, value, h);
                }
                else {
                    result[*returnSize] = malloc(sizeof(int)*4);
                    result[*returnSize][0] = nums[i]; 
                    result[*returnSize][1] = nums[j]; 
                    result[*returnSize][2] = nums[k];
                    result[*returnSize][3] = h->value;
                    (*returnSize)++;
                
                   // 去除重复值。
                    while (k+1 < numsSize && nums[k+1] == nums[k])
                        k++;
                }
            }
            if (nums[j] > 0 && nums[j] >= target)
                break;
        }
        if (nums[i] > 0 && nums[i] >= target)
            break;
    }
    
    return result;
}