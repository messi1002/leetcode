// 1.暴力求解
public int findPeakElement(int[] nums) {
    int len = nums.length;
    for (int i = 0; i < len - 1; i++) {
        // 不需要判断nums[i]>nums[i-1]，因为当nums[i-1]<=nums[i]时，才能进到这次循环中。
        if (nums[i] > nums[i + 1]) {
            return i;
        }
    }  
    return len - 1;
}

// 2.二分查找
public int findPeakElement(int[] nums) {
    int left = 0, right = nums.length - 1;
    while (left < right) {
        int mid = (left + right) >>> 1;
        // 右侧存在峰值
        if (nums[mid] < nums[mid + 1]) {
            left = mid + 1;
        }
        // 左侧存在峰值，且可能是nums[mid]。
        else {
            right = mid;
        }
    }
    // 返回right也可以
    return left;
}