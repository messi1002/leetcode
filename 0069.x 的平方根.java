// 1.库函数
public int mySqrt(int x) {
    return (int) Math.sqrt(x);
}

// 2.二分查找
public int mySqrt(int x) {
    long left = 0, right = x;
    while (left <= right) {
        long mid = (left + right) >>> 1;
        if (mid * mid == x) {
            return (int) mid;
        }
        else if (mid * mid < x) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    // 查找比target(x的平方根)小的最大整数的位置，套用模板可知，返回right。
    return (int) right;
}



// 5.牛顿迭代法(待写)
