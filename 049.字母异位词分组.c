1.暴力解法

char*** groupAnagrams(char** strs, int strsSize, int** columnSizes, int* returnSize) {
    char*** result = malloc(sizeof(char**)*strsSize);
    int** hash = malloc(sizeof(int*)*strsSize);
    int* flag = malloc(sizeof(int)*strsSize);
    int col = 0, row = 0;
    // 记录输出数组每行的列数。
    *columnSizes = malloc(sizeof(int)*strsSize);
    *returnSize = 0;
    
    for (int i = 0; i < strsSize; i++) {
        int len = strlen(strs[i]);
        hash[i] = malloc(sizeof(int)*26);
        // flag数组记录单词是否已经分组。
        flag[i] = 0;
        
        // 用哈希表分别记录每个单词中不同字母的出现次数。
        for (int j = 0; j < len; j++)
            hash[i][strs[i][j]-'a']++;    
    }
    for (int i = 0; i < strsSize; i++) {
        // 确定新行字符串，然后寻找它的字母异位词。
        if (flag[i] == 0) {
            result[row] = malloc(sizeof(char*)*strsSize);
            result[row][col] = malloc(sizeof(char)*24);
            flag[i] = 1;
            strcpy(result[row][col], strs[i]);
            col++;
            (*returnSize)++;
        
            // 在未分组的字符串中寻找新行字符串的字母异位词。
            for (int j = i+1; j < strsSize; j++) {
                if (flag[j] == 0) {
                    for (int k = 0; k < 26; k++) {
                        if (hash[i][k] != hash[j][k])
                            break;
                        if (k == 25) {
                            result[row][col] = malloc(sizeof(char)*24);
                            flag[j] = 1;
                            strcpy(result[row][col], strs[j]);
                            col++;
                        }
                    }
                }
            }

            (*columnSizes)[row] = col;
            // 换行。
            row++;
            col = 0;
        }
    }
    
    return result;
}


2.排序+哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
// 快排函数(升序)。
int compare(const void* a, const void* b) {
    return (*(char*)a)-(*(char*)b);
}

char*** groupAnagrams(char** strs, int strsSize, int** columnSizes, int* returnSize) {
    struct hash {
        char key[24];
        // 行。
        int value1;
        // 列。
        int value2;
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;    
    char*** result = malloc(sizeof(char**)*strsSize);
    int row = 0;
    // 记录输出数组每行的列数。
    *columnSizes = malloc(sizeof(int)*strsSize);
    
    for (int i = 0; i < strsSize; i++) {
        struct hash *h;
        char s[24] = "";
        strcpy(s, strs[i]);
        qsort(s, strlen(s), sizeof(char), compare);
        HASH_FIND_STR(hashTable, s, h);    
        
        // 确定新行字符串，并用哈希表记录。
        if (!h) {
            h = malloc(sizeof(struct hash));
            strcpy(h->key, s);
            h->value1 = row;
            h->value2 = 1;
            HASH_ADD_STR(hashTable, key, h);
            
            result[row] = malloc(sizeof(char*)*strsSize);
            result[row][0] = malloc(sizeof(char)*24);
            strcpy(result[row][0], strs[i]);
            row++;
        }
        // 添加到它的字母异位词所在的行。
        else {
            result[h->value1][h->value2] = malloc(sizeof(char)*24);
            strcpy(result[h->value1][h->value2], strs[i]);
            h->value2++;
        }
    }

    *returnSize = row;

    // 遍历哈希表。
    for (struct hash *s = hashTable; s!= NULL; s = s->hh.next)
        (*columnSizes)[s->value1] = s->value2;        
    
    return result;
}


3.字符串+哈希表(uthash)

char*** groupAnagrams(char** strs, int strsSize, int** columnSizes, int* returnSize) {
    struct hash {
        char key[60];
        // 行。
        int value1;
        // 列。
        int value2;
        UT_hash_handle hh; 
    };
    
    struct hash *hashTable = NULL;    
    char*** result = malloc(sizeof(char**)*strsSize);
    int row = 0;
    // 记录输出数组每行的列数。
    *columnSizes = malloc(sizeof(int)*strsSize);

    for (int i = 0; i < strsSize; i++) {
        int hash[26] = {0}, len = strlen(strs[i]);
        char s[60] = "";
        
        // 对每个字符串进行字符计数，数字间用"#"分割。
        for (int j = 0; j < len; j++) 
            hash[strs[i][j]-'a']++;
        for (int j = 0; j < 26; j++) {
            char s1[2];
            s1[0] = hash[j];
            s1[1] = '\0';
            strcat(s, s1);
            strcat(s, "#");
        }
        
        struct hash *h;
        HASH_FIND_STR(hashTable, s, h);    
        
        // 将计数结果当作键值保存，字母异位词的计数结果相同。
        if (!h) {
            h = malloc(sizeof(struct hash));
            strcpy(h->key, s);
            h->value1 = row;
            h->value2 = 1;
            HASH_ADD_STR(hashTable, key, h);
            
            result[row] = malloc(sizeof(char*)*strsSize);
            result[row][0] = malloc(sizeof(char)*24);
            strcpy(result[row][0], strs[i]);
            row++;
        }
        // 添加到它的字母异位词所在的行。
        else {
            result[h->value1][h->value2] = malloc(sizeof(char)*24);
            strcpy(result[h->value1][h->value2], strs[i]);
            h->value2++;
        }
    }

    *returnSize = row;

    // 遍历哈希表。
    for (struct hash *s = hashTable; s!= NULL; s = s->hh.next)
        (*columnSizes)[s->value1] = s->value2;        
    
    return result;
}