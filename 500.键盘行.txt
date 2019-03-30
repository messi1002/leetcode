1.哈希表

char** findWords(char** words, int wordsSize, int* returnSize) {
    // 用哈希表记录26个字母所处的键盘行数。
    int hash[26] = {1, 0, 0, 1, 2, 1, 1, 1, 2, 1, 1, 1, 0, 0, 2, 2, 2, 2, 1, 2, 2, 0, 2, 0, 2, 0};
    int** result = malloc(sizeof(int*)*wordsSize);
    *returnSize = 0;
    
    for (int i = 0; i < wordsSize; i++) {
        // 用mark记录每行第一个字母所处的键盘行数。
        int mark = hash[tolower(words[i][0])-'a'], len = strlen(words[i]), j = 0;
        
        while (j < len) {
            // 查看之后的字母是否和第一个字母处于键盘的同一行。
            if (hash[tolower(words[i][j])-'a'] != mark)
                break;   
            else if (j == len-1) {
                result[*returnSize] = malloc(sizeof(int)*len);
                result[*returnSize] = words[i];
                (*returnSize)++;
            }
            
            j++;
        }
    }

    return result;
}