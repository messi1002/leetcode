// 1.暴力求解
public int peakIndexInMountainArray(int[] A) {
    int i = 0;
    // 题目给定一个确定为山脉的数组，所以不用担心索引越界。
    while (A[i] < A[i + 1]) {
        i++;
    }
    return i;
}

// 2.二分查找
public int peakIndexInMountainArray(int[] A) {
    int left = 0, right = A.length - 1;
    while (left <= right) {
        int mid = (left + right) >>> 1;
        if (A[mid] > A[mid - 1] && A[mid] > A[mid + 1]) {
            return mid;
        }
        if (A[mid] > A[mid - 1] && A[mid] < A[mid + 1]) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    return -1;
}