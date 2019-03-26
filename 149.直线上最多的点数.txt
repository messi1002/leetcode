1.暴力解法

int maxPoints(struct Point* points, int pointsSize) {
    if (pointsSize < 3)
        return pointsSize;
    
    int max = 0;
    
    for (int i = 0; i < pointsSize; i++) {
        for (int j = i+1; j < pointsSize; j++) {
            int count = 2;
            
            for (int k = 0; k < pointsSize; k++) {
                if (k != i && k != j) {
                    int x1 = points[i].x, y1 = points[i].y, x2 = points[j].x, y2 = points[j].y, x3 = points[k].x, y3 = points[k].y;
                    
                    // 若x1等于x2，则x3必须在直线x = x1/x2上。
                    if (x1 == x2 && x3 == x1)
                        count++;
                    // 两点式(y-y2) = (y2-y1)*(x-x2)/(x2-x1)成立的条件是分母不为0。
                    else if (x1 != x2 && (long int)(y3-y2)*(x2-x1) == (long int)(y2-y1)*(x3-x2))
                        count++;    
                }                
            }    
            
            // 更新最大值。
            max = max > count? max: count;
        }
    }
    
    return max;
}  

    
2.数学+哈希表(uthash)

// uthash是一个用c语言编写的开源库，使用宏实现了哈希表的增删改查等功能。
int maxPoints(struct Point* points, int pointsSize) {
    struct hash {
        char key[30];
        int count;
        UT_hash_handle hh; 
    };
    
    int max = 0;
    
    for (int i = 0; i < pointsSize; i++) {
        struct hash *hashTable = NULL;
        int c1 = 0, c2 = 1;

        for (int j = 0; j < pointsSize; j++) {
            int m = points[i].x-points[j].x, n = points[i].y-points[j].y;
            
            if (j != i) {
                // 重复的点用c1记录，最后直接加给c2。
                if (points[i].x == points[j].x && points[i].y == points[j].y)
                    c1++;
                // 斜率存在时。
                else if (m != 0) {
                    struct hash *h;
                    double k = n == 0? 0.0: (double)m / n;
                    char s[30];
                    // 将斜率的计算结果保留到小数点后20位，并转为字符串。
                    sprintf(s, "%.20f", k);
                    HASH_FIND_STR(hashTable, s, h);
                
                    if (h)
                        h->count++;
                    else {
                        h = malloc(sizeof(struct hash));
                        strcpy(h->key, s);
                        // 记录斜率为该键值的直线上的点的个数(包括该点)。
                        h->count = 2;
                        HASH_ADD_STR(hashTable, key, h);
                    }
                }
                // 斜率不存在时(90°)。
                else
                    c2++;
            }
        }  
           
        // 此时c2的初值为斜率不存在的直线上的点的个数(包括该点)。
        if (hashTable) {
            // 逐一与其他直线上的点的个数作比较，找出最大值。
            for (struct hash *s = hashTable; s!= NULL; s = s->hh.next)
                c2 = s->count > c2? s->count: c2;
        }  
        
        // 更新最大值。
        max = c1+c2 > max? c1+c2: max;
    }
    
    return pointsSize == 0? 0: max;
}