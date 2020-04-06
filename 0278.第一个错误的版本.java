// 1.暴力求解(超时)
public int firstBadVersion(int n) {
    for (int i = 1; i < n; i++) {
        if (isBadVersion(i) == true) {
            return i;
        }
    }
    return n;
}

// 2.二分查找
public int firstBadVersion(int n) {
    int left = 1, right = n;
    while (left <= right) {
        int mid = (left + right) >>> 1;
        if (isBadVersion(mid) == false) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    // 这道题的target是false，然后需要寻找false后的第一个true，套用模板可知，返回left。
    return left;
}