1.暴力解法

int numJewelsInStones(char* J, char* S) {
    int count = 0, len_S = strlen(S), len_J = strlen(J);

    for (int i = 0; i < len_S; i++) {
        for (int j = 0; j < len_J; j++) {
            if(J[j] == S[i]) {
                ++count;
                break;
            }   
        }            
    }
            
    return count;
}


2.哈希表

int numJewelsInStones(char* J, char* S) {
    int hash[52] = {0}, len_J = strlen(J), len_S = strlen(S), count = 0;
    
    // 字母包括小写字母和大写字母。
    // 将J中字母存入哈希表中。
    for (int i = 0; i < len_J; i++) {
        if (islower(J[i]))
            hash[J[i]-'a'] = 1;
        else 
            hash[J[i]-'A'+26] = 1;
    }
    // 判断石头字母是不是宝石。
    for (int i = 0; i < len_S; i++) {
        if ((islower(S[i]) && hash[S[i]-'a']) || (!islower(S[i]) && hash[S[i]-'A'+26]))
            count++;
    }
    
    return count;
}


3.uthash

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
int numJewelsInStones(char* J, char* S) {
    struct hash {
        int value;
        // makes this structure hashable.
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;
    int len_J = strlen(J), len_S = strlen(S), count = 0;
    
    for (int i = 0; i < len_J; i++) {
        struct hash *h = malloc(sizeof(struct hash));
        h->value = J[i];
        // 将J中字母存入哈希表中。
        HASH_ADD_INT(hashTable, value, h);
    }
    for (int i = 0; i < len_S; i++) {
        struct hash *h;
        int n = S[i];
        HASH_FIND_INT(hashTable, &n, h);
        
        // 判断石头字母是不是宝石。
        if (h)
            count++;
    }
    
    return count;
}