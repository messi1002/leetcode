1.暴力解法改进

int countPrimes(int n) {
    int count = 0;
    
    if (n > 2)
        count++;
    if (n > 3)
        count++;
    
    // 先排除偶数。
    for (int i = 5; i < n; i += 2) {
        int s = sqrt(i);
        
        // 根据质数性质，只需要判断其是否可以整除2-sqrt(i)，就可知其是否是质数。
        for (int j = 2; j <= s; j++) {
            if (i % j == 0)
                break;
            if (j == s)
                count++;
        }
    }
    
    return count;
}


2.哈希表+埃拉托斯特尼筛法

int countPrimes(int n) {
    int count = 0, s = sqrt(n);
    int* arr = malloc(sizeof(int)*n);
    
    if (n > 2) {
        // 初始化哈希表。
        for (int i = 2; i < n; i++) 
            arr[i] = 1;
        for (int i = 2; i <= s; i++) {
            if (arr[i]) {
                // 注意:是剔除其倍数，而不是平方数。
                for (int j = i*2; j < n; j += i)
                    arr[j] = 0;
            }
        }
        // 计数质数(剔除了所有非质数)。
        for (int i = 2; i < n; i++) {
            if (arr[i])
                count++;
        }
    }
    
    return count;
}