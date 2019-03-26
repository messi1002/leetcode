1.数学 + 哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
// 哈希表(uthash)。
int numberOfBoomerangs(int** points, int pointsRowSize, int pointsColSize) {
    struct hash {
        int key;
        int count;
        UT_hash_handle hh; 
    };
    
    int count = 0;
    
    for (int i = 0; i < pointsRowSize; i++) {
        struct hash *hashTable = NULL;
        
        for (int j = 0; j < pointsRowSize; j++) {
            if (j != i) {
                struct hash *h;
                int p = points[i][0]-points[j][0], q = points[i][1]-points[j][1];
                int n = p*p + q*q;
                HASH_FIND_INT(hashTable, &n, h);
                
                // 记录不同键值(距离)的出现次数。
                if (h)
                    h->count++;
                else {
                    h = malloc(sizeof(struct hash));
                    h->key = n;
                    h->count = 1;
                    HASH_ADD_INT(hashTable, key, h);
                }
            }    
        }
        // 计算某个点的总回旋镖数。
        for (struct hash *s = hashTable; s!= NULL; s = s->hh.next)
            count += s->count*(s->count-1); 
    }
    
    return count;
}