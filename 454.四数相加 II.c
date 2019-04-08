1.哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
int fourSumCount(int* A, int ASize, int* B, int BSize, int* C, int CSize, int* D, int DSize) {
    struct hash {
        int value;
        int count;
        UT_hash_handle hh; 
    };
            
    struct hash *hashTable = NULL;    
    int count = 0;
    
    // 将(A[i]+B[j])的每一种可能值放入哈希表中，并记录其出现次数。
    for (int i = 0; i < ASize; i++) {
        for (int j = 0; j < BSize; j++) {
            int n = -A[i]-B[j];            
            struct hash *h;
            HASH_FIND_INT(hashTable, &n, h);
            
            if (!h) {
                h = malloc(sizeof(struct hash));
                h->value = n;
                h->count = 1;
                HASH_ADD_INT(hashTable, value, h);
            }
            else
                h->count++;
        }
    }
    // 在哈希表中查找是否存在(C[i]+D[j])的每一种可能值。
    for (int i = 0; i < CSize; i++) {
        for (int j = 0; j < DSize; j++) {
            int m = C[i]+D[j];
            struct hash *h;
            HASH_FIND_INT(hashTable, &m, h);
                
            if (h)
                count += h->count;
        }
    }
    
    return count;
}