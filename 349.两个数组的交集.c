1.哈希表

int* intersection(int* nums1, int nums1Size, int* nums2, int nums2Size, int* returnSize) {
    // 哈希表。
    int hash[1000] = {0};
    int* result = malloc(sizeof(int)*nums1Size);
    *returnSize = 0;

    for (int i = 0; i < nums1Size; i++)
        hash[nums1[i]] = 1;
    for (int i = 0; i < nums2Size; i++) {
        // 查找重复数字。
        if (hash[nums2[i]] == 1) {
            result[(*returnSize)++] = nums2[i];
            // 去重。
            hash[nums2[i]] = 0;
        }
    }
    
    return result;
}


2.uthash

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
int* intersection(int* nums1, int nums1Size, int* nums2, int nums2Size, int* returnSize) {
    struct hash {
        int value;
        // makes this structure hashable.
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    int* result = malloc(sizeof(int)*nums1Size);
    
    for (int i = 0; i < nums1Size; i++) {
        struct hash *h;
        // 查找值。
        HASH_FIND_INT(hashTable, nums1+i, h);
        
        if (!h) {
            h = malloc(sizeof(struct hash));
            h->value = nums1[i];
            // 若值不存在则添加。
            HASH_ADD_INT(hashTable, value, h);
        }
    }
    for (int i = 0; i < nums2Size; i++) {
        struct hash *h;
        HASH_FIND_INT(hashTable, nums2+i, h);
        
        // 找到交集元素后，得从哈希表中删除它(因为输出结果中的每个元素是唯一的)。
        if (h) {
            result[(*returnSize)++] = h->value;
            HASH_DEL(hashTable, h);
        }
    }
    
    return result;
}


3.排序 + 双指针查找

// 快排函数(升序)。
int compare(const void* a, const void* b) {
    return (*(int*)a)-(*(int*)b); 
}

int* intersection(int* nums1, int nums1Size, int* nums2, int nums2Size, int* returnSize) {    
    // 排序。
    qsort(nums1, nums1Size, sizeof(int), compare);
    qsort(nums2, nums2Size, sizeof(int), compare);
    
    int pos1 = 0, pos2 = 0; 
    int* result = malloc(sizeof(int)*nums1Size);
    *returnSize = 0;
    
    // 双指针。
    while (pos1 < nums1Size && pos2 < nums2Size) {
        if (nums1[pos1] < nums2[pos2])
            pos1++;
        else if (nums1[pos1] > nums2[pos2])
            pos2++;
        else {
            // 去重。
            if (*returnSize == 0 || nums1[pos1] != result[*returnSize-1])
                result[(*returnSize)++] = nums1[pos1];
            
            pos1++;
            pos2++;
        }
    }
    
    return result;
}