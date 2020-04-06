// 1.暴力求解
public boolean searchMatrix(int[][] matrix, int target) {
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[i][j] == target) {
                return true;
            }
        }
    }
    return false;
}

// 2.模拟 搜索二维矩阵II
// 观察二维数组的存储规律，发现可以转化为线性查找(从左下角或者右上角开始查找)。
public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0) {
        return false;
    }
    int len = matrix.length, i = 0, j = matrix[0].length - 1;
    while (i < len && j >= 0) {
        if (matrix[i][j] == target) {
            return true;
        }
        // 向左走
        else if (matrix[i][j] > target) {
            j--;
        }
        // 向下走
        else {
            i++;
        }
    }
    return false;
}

// 3.改进 搜索二维矩阵II
// 先定位行
public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0) {
        return false;
    }
    int len1 = matrix.length, len2 = matrix[0].length, i = 0, j = 0;
    while (i < len1 && j < len2) {
        if (matrix[i][j] == target) {
            return true;
        }
        // 向下走
        else if (i + 1 < len1 && matrix[i + 1][j] <= target) {
            i++;
        }
        // 向右走
        // 再定位列
        else {
            j++;
        }
    }
    return false;
}

// 4.两次二分查找
// 第一次二分查找定位行，第二次二分查找定位列。
public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0 || matrix[0].length == 0) {
        return false;
    }
    int len1 = matrix.length, len2 = matrix[0].length;
    int l1 = 0, l2 = 0, r1 = len1 - 1, r2 = len2 - 1;
    // 第一次二分查找定位行
    while (l1 <= r1) {
        int mid = (l1 + r1) >>> 1;
        if (matrix[mid][0] == target) {
            return true;
        }
        else if (matrix[mid][0] < target) {
            l1 = mid + 1;
        }
        else {
            r1 = mid - 1;
        }
    }
    if (r1 < 0) {
        return false;
    }
    // 第二次二分查找定位列
    while (l2 <= r2) {
        int mid = (l2 + r2) >>> 1;
        if (matrix[r1][mid] == target) {
            return true;
        }
        else if (matrix[r1][mid] < target) {
            l2 = mid + 1;
        }
        else {
            r2 = mid - 1;
        }
    }
    return false;
}

// 5.一次二分查找
// 将二维矩阵看成一个长度为 matrix.length*matrix[0].length 的一维数组，直接对其进行二分查找。
public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix.length == 0 || matrix[0].length == 0) {
        return false;
    }
    int len1 = matrix.length, len2 = matrix[0].length;
    int left = 0, right = len1 * len2 - 1;
    while (left <= right) {
        // 将二维矩阵看成一个一维数组
        int mid = (left + right) >>> 1, i = mid / len2, j = mid % len2;
        if (matrix[i][j] == target) {
            return true;
        }
        else if (matrix[i][j] < target) {
            left = mid + 1;
        }
        else {
            right = mid - 1;
        }
    }
    return false;
}