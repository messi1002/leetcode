1.uthash

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
int* intersect(int* nums1, int nums1Size, int* nums2, int nums2Size, int* returnSize) {    
    struct hash {
        int value;
        int count;
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
            h->count = 1;
            // 若值不存在则添加，并令出现次数==1。
            HASH_ADD_INT(hashTable, value, h);
        }
        // 若值已存在则出现次数+1。
        else
            h->count++;
    }
    for (int i = 0; i < nums2Size; i++) {
        struct hash *h;
        HASH_FIND_INT(hashTable, nums2+i, h);
        
        if (h && h->count > 0) {
            result[(*returnSize)++] = h->value;
            h->count--;
        }
    }
    
    return result;
}


2.排序 + 双指针查找

// 快排函数(升序)。
int compare(const void* a, const void* b) {
    /*
     bug: 若是将 > 改为 -,可能溢出整型范围。
     如测试数据: [-2147483648,1],在排序时会导致异常: runtime error: signed integer overflow: -2147483648 - 1 cannot be represented in type 'int' (solution.c)
     */
    return *(int*)a > *(int*)b; 
}

int* intersect(int* nums1, int nums1Size, int* nums2, int nums2Size, int* returnSize) {    
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
            result[(*returnSize)++] = nums1[pos1];
            pos1++;
            pos2++;
        }
    }
    
    return result;
}