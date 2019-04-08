1.哈希表

int firstUniqChar(char* s) {
    int hash[26] = {0}, i, len = strlen(s);
    
    // 用哈希表记录每个字母的出现次数。
    for (i = 0; i < len; i++)
        hash[s[i]-'a']++;
    // 寻找第一个唯一字符。
    for (i = 0; i < len; i++) {
        if (hash[s[i]-'a'] == 1)
            break;
    }
    
    return i == len? -1: i;
}