1.字符串 + 数组

bool wordPattern(char* pattern, char* str) {
    int len = (strlen(str)/2)+2, size = 0;
    char** arr = malloc(sizeof(char*)*len);
    
    for (int i = 0; i < len; i++)
        arr[i] = malloc(sizeof(char)*15);
    
    arr[size] = strtok(str, " ");
    len = strlen(pattern);
    
    // 以空格为分割符分割str字符串。
    while (arr[size] != NULL) {
        size++;
        arr[size] = strtok(NULL, " ");
    
        // 若字符串个数与字符个数不等，则不匹配。
        if (arr[size] == NULL && size != len)
            return false;
    }
    
    for (int i = 0; i < len; i++) {
        for (int j = i+1; j < len; j++) {
            /*
             以下两种情况返回false:
             1.pattern中的相同字符映射了str中的不同字符串。
             2.str中的相同字符串映射了pattern中的不同字符。
             */
            if ((pattern[i] == pattern[j] && strcmp(arr[i], arr[j]) != 0) || (pattern[i] != pattern[j] && strcmp(arr[i], arr[j]) == 0))
                    return false;
        }
    }

    return true;
}