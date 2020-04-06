// 1.暴力求解(超时)
public boolean isPerfectSquare(int num) {
    for (int i = 0; i <= num; i++) {
        if (i * i == num) {
            return true;
        }
    }
    return false;
}

// 2.二分查找
public boolean isPerfectSquare(int num) {
    long left = 0, right = num;
    while (left <= right) {
        long mid = (left + right) >>> 1;
        if (mid * mid == num) {
            return true;
        }
        else if (mid * mid < num) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    return false;
}

// 3.数学公式
// 任意一个完全平方数可以表示成前n个连续奇数的和: 1+3+5+...+(2n-3)+(2n-1)=(2n-1+1)*n/2=n^2
public boolean isPerfectSquare(int num) { 
    int i = 1;
    while (num > 0) {
        num -= i;
        i += 2;
    }
    return num == 0? true: false;
}

// 4.牛顿迭代法(待写)