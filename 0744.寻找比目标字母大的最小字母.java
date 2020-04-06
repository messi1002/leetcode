// 1.暴力解法
public char nextGreatestLetter(char[] letters, char target) {
    int len = letters.length;
    for (int i = 0; i < len; i++) {
        if (letters[i] > target) {
            return letters[i];
        }
    }
    // 判断目标字母是否大于所有字母，若是，则按题意返回数组中的第一个字母。
    return letters[0];
}

// 2.二分查找
public char nextGreatestLetter(char[] letters, char target) {
    int left = 0, right = letters.length - 1;
    while (left <= right) {
        int mid = (left + right) >>> 1;
        // 排除小于或等于目标字母的字母(将这两种情况合并了)
        if (letters[mid] <= target) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    // 判断目标字母是否大于所有字母，若是，则按题意返回数组中的第一个字母。
    return left == letters.length? letters[0]: letters[left];
}