1.哈希表

int longestPalindrome(char* s) {
    int hash[52] = {0}, flag = 1, len = strlen(s), result = 0;
    
    // 用哈希表记录每个字母的出现次数。
    for (int i = 0; i < len; i++) {
        if (s[i] >= 'a')
            hash[s[i]-'a'+26]++;
        else
            hash[s[i]-'A']++;
    }
    for (int i = 0; i < 52; i++) {
        if (hash[i] > 0) {
            // 一个字母可以作为回文串的中心(1次)。
            if (hash[i] % 2 != 0 && flag == 1) {
                result++;
                flag = 0;
            }
            
            // 除中心外的对称位置必须是相同的字母(即出现偶数次的字母)。
            result += (hash[i]/2) * 2;
        }
    }
    
    return result;
}