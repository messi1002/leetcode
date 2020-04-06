// 1.暴力法求解: 超时
int trailingZeroes(int n){
    int count = 0;
    // 计算i的乘法因子中有几个5
    for (int i = 5; i <= n; i += 5) {
        int j = i;
        while (j % 5 == 0) {
            j /= 5;
            count += 1;
        }
    }
    return count;
}

// 2.进一步的规律
int trailingZeroes(int n){
    int count = 0;
    while (n > 0) {
        count += n / 5;
        n /= 5;
    }
    return count;
}