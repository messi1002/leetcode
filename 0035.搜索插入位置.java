# 1.暴力解法
public int searchInsert(int[] nums, int target) {
    int len = nums.length;
    for (int i = 0; i < len; i++) {
        if (nums[i] >= target) {
            return i;
        }
    }
    return len;
}

# 2.二分查找
public int searchInsert(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        // int mid = (left + right) / 2;
        // int mid = left + (right - left) / 2;
        int mid = (left + right) >>> 1;
        if (nums[mid] == target) {
            return mid;
        }
        else if (nums[mid] < target) {
            left = mid + 1;
        }   
        else {
            right = mid - 1;
        }
    }
    return left;
}