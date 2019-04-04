1.暴力解法改进

int* dailyTemperatures(int* T, int TSize, int* returnSize) {
    int* result = (int*)malloc(sizeof(int)*TSize);
    *returnSize = TSize;
    result[TSize-1] = 0;
    
    for (int i = 0; i < TSize-1; i++) {
        // 改进:前后两天温度相等时，不用再重复判断。
        if (i > 0 && T[i] == T[i-1])
            result[i] = result[i-1] == 0? 0: result[i-1]-1;
        else {
            for (int j = i+1; j < TSize; j++) {
                if (T[j] > T[i]) {
                    result[i] = j-i;
                    break;
                } 
                // 温度不会再升高。
                if (j == TSize-1)
                    result[i] = 0;
            }
        }
    }
        
    return result;
}


2.栈

int* dailyTemperatures(int* T, int TSize, int* returnSize) {
    int* result = (int*)malloc(sizeof(int)*TSize);
    // 用栈记录T的下标。
    int* stack_index = malloc(sizeof(int)*TSize);
    *returnSize = TSize;
    result[TSize-1] = 0;
    // 栈顶指针。
    int top = 0;
    
    for (int i = 0; i < TSize; i++)
        result[i] = 0;
    for (int i = 0; i < TSize; i++) {
        // 若当前元素大于栈顶元素，栈顶元素出栈。即温度升高了，所求天数为两者下标的差值。
        while (top > 0 && T[i] > T[stack_index[top-1]]) {
            result[stack_index[top-1]] = i-stack_index[top-1];
            top--;
        }
        
        // 当前元素入栈。
        stack_index[top] = i;
        top++;
    }
    
    return result;
}