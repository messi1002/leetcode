// 1.暴力求解
public boolean findNumberIn2DArray(int[][] matrix, int target) {
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[i][j] == target) {
                return true;
            }
        }
    }
    return false;
}

// 2.线性查找
// 观察二维数组的存储规律，发现可以转化为线性查找(从左下角或者右上角开始查找)。
public boolean findNumberIn2DArray(int[][] matrix, int target) {
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