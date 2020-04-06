// 1.暴力求解
public int arrangeCoins(int n) {
    int i = 1;
    while (n - i >= 0) {
        n -= i++;
    }
    return i - 1;
}

// 2.二分查找
public int arrangeCoins(int n) {
    long left = 0, right = n;
    while (left <= right) {
        // sum: 形成mid行完整阶梯所需要的硬币数(从第1行到第mid行需要mid(mid+1)/2个硬币)
        long mid = (left + right) >>> 1, sum = mid * (mid + 1) / 2;
        if (sum == n) {
            return (int) mid;
        }
        else if (sum < n) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    // 查找比target小的"最大数"
    return (int) right;
}

// 3.数学公式
// 已知从第1行到第m行需要m(m+1)/2个硬币，所以列方程有:m(m+1)=2n，然后求解出最大的m即可。
public int arrangeCoins(int n) {
    // 把int类型转换成long类型是防止(n*8)的值溢出整型范围
    return (int) (-1 + Math.sqrt(1 + 8 * (long) n)) / 2;
}