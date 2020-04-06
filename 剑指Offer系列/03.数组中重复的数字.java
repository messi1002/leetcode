// 1.哈希表
public int findRepeatNumber(int[] nums) {
    int len = nums.length, res = -1;
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < len; i++) {
        // 利用add方法的返回值判断
        if (!set.add(nums[i])) {
            res = nums[i];
            break;
        }
    }
    return res;
}

// 2.技巧: 下标定位法(使用数组本身作为哈希表)
// 将数字i放在下标为i的位置，如果有重复的数字出现(即发现同一位置上有多个数字)，则返回ture。
public int findRepeatNumber(int[] nums) {
    int len = nums.length;
    for (int i = 0; i < len; i++) {
        // 将数字i放在下标为i的位置
        while (nums[i] != i) {
            // 有重复的数字出现
            if (nums[nums[i]] == nums[i]) {
                return nums[i];
            }
            int temp = nums[nums[i]];
            nums[nums[i]] = nums[i];
            nums[i] = temp;
        }
    }
    return -1;
}