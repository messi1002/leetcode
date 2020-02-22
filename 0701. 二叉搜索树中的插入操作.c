// 1.尾递归
struct TreeNode* insertIntoBST(struct TreeNode* root, int val) {
    // 找到插入位置
    if (!root) {
        struct TreeNode* node = malloc(sizeof(struct TreeNode));
        node->val = val;
        node->left = NULL;
        node->right = NULL;
        return node;
    }
    if (val < root->val) {
        root->left = insertIntoBST(root->left, val);
    }
    else {
        root->right = insertIntoBST(root->right, val);
    }        
    return root;
}

// 2.迭代
struct TreeNode* insertIntoBST(struct TreeNode* root, int val) {
    struct TreeNode* t = root;
    while (t) {
        if (val < t->val) {
            // 找到插入位置
            if (!t->left) {
                struct TreeNode* node = malloc(sizeof(struct TreeNode));
                node->val = val;
                node->left = NULL;
                node->right = NULL;
                t->left = node;
                break;
            }
            t = t->left;
        }
        else {
            // 找到插入位置
            if (!t->right) {
                struct TreeNode* node = malloc(sizeof(struct TreeNode));
                node->val = val;
                node->left = NULL;
                node->right = NULL;
                t->right = node;
                break;
            }
            t = t->right;
        }   
    }    
    return root;
}