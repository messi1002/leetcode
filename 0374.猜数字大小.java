// 1.暴力求解(超时)
public int guessNumber(int n) {
    for (int i = 1; i <= n; i++) {
        if (guess(i) == 0) {
            return i;
        }
    }
    return -1;
}

// 2.二分查找
public int guessNumber(int n) {
    int left = 1, right = n;
    while (left <= right) {
        int mid = (left + right) >>> 1;
        if (guess(mid) == 0) {
            return mid;
        }
        else if (guess(mid) == 1) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    // 题目会保证一定能猜到数字，所以这里返回任意数字都可以。
    return -1;
 }