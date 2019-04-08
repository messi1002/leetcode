1.哈希表

bool isIsomorphic(char* s, char* t) {
    int hash1[128] = {0}, hash2[128] = {0}, len = strlen(s);
    
    for (int i = 0; i < len; i++) {
        /*
         以下两种情况返回false:
            1.t中的相同字符映射了b中的不同字符。
            2.b中的相同字符映射了t中的不同字符。
         */
        if ((hash1[s[i]] == 0 && hash2[t[i]+1] != 0) || (hash1[s[i]] > 0 && hash1[s[i]] != t[i]+1))
            return false;
        else {
            hash1[s[i]] = t[i]+1;
            hash2[t[i]+1] = 1;
        }
    }
                 
    return true;
}