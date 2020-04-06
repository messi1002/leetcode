// 1.尾递归
struct TreeNode* searchBST(struct TreeNode* root, int val) {
    if (!root) {
        return NULL;
    }
    if (val == root->val) {
        return root;
    }
    else if (val < root->val) {
        return searchBST(root->left, val);
    }
    else {
        return searchBST(root->right, val);
    }
}

// 2.迭代
struct TreeNode* searchBST(struct TreeNode* root, int val) {
    while (root) {
        if (val == root->val) {
            return root;
        }
        else if (val < root->val) {
            root = root->left;
        }
        else {
            root = root->right;
        }
    }
    return NULL;
}