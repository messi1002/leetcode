1.哈希表

int repeatedNTimes(int* A, int ASize) {
    int hash[10000] = {0};
    
    for (int i = 0; i < ASize; i++)
        hash[A[i]]++;
    for (int i = 0; i < 10000; i++) {
        if (hash[i] == ASize/2)
            return i;
    }
    
    return 0;
}


2.uthash

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
int repeatedNTimes(int* A, int ASize) {
    struct hash {
        int value;
        int count;
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    
    for (int i = 0; i < ASize; i++) {
        struct hash *h;
        HASH_FIND_INT(hashTable, A+i, h);
        
        if (h)
            h->count++;
        else {
            h = malloc(sizeof(struct hash));
            h->value = A[i];
            h->count = 1;
            HASH_ADD_INT(hashTable, value, h);
        }
                
        if (h->count == ASize/2)
            return A[i];
    }
    
    return 0;
}


3.技巧—哈希表

int repeatedNTimes(int* A, int ASize) {
    int hash[10000] = {0};
    
    for (int i = 0; i < ASize; i++) {
        if (hash[A[i]] == 1)
            return A[i];
        else
            hash[A[i]]++;
    }
    
    return 0;
}


4.技巧—uthash

int repeatedNTimes(int* A, int ASize) {
    struct hash {
        int value;
        // makes this structure hashable.
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    
    for (int i = 0; i < ASize; i++) {
        struct hash *h;
        HASH_FIND_INT(hashTable, A+i, h);
        
        if (h)
            return A[i];
        else {
            h = malloc(sizeof(struct hash));
            h->value = A[i];
            HASH_ADD_INT(hashTable, value, h);
        }
    }
    
    return 0;
}


5.技巧—暴力解法

int repeatedNTimes(int* A, int ASize) {
    for (int i = 0; i < ASize; i++) {
        for (int j = i+1; j < ASize; j++) {
            if (A[i] == A[j])
                return A[i];
        }
    }
    
    return 0;
}


6.终极技巧

int repeatedNTimes(int* A, int ASize) {
    for (int i = 2; i < ASize; i++) {
        if (A[i] == A[i-1] || A[i] == A[i-2])
            return A[i];
    }
    
    return A[0];
}