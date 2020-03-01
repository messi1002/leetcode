// 1.用被删除节点的前驱节点(是其左子树中的最大值所在的节点)替换被删除的节点
// 返回以root为根节点的二分搜索树中的最大节点
int findMax(struct TreeNode* root) {
    if (!root->right) {
        return root->val;
    }
    return findMax(root->right);
}

struct TreeNode* deleteNode(struct TreeNode* root, int key) {
    if (!root) {
        return NULL;
    }
    if (key < root->val) {
        root->left = deleteNode(root->left, key);
    }
    else if (key > root->val) {
        root->right = deleteNode(root->right, key);
    }
    else {
        if (!root->left) {
            return root->right;
        }
        else if (!root->right) {
            return root->left;
        }
        else {
            // 此时root是被删除的节点
            // max是被删除节点的前驱节点的值
            int max = findMax(root->left);
            // 将被删除节点的前驱节点放在被删除节点的位置
            root->val = max;
            // 删除原来的前驱节点
            root->left = deleteNode(root->left, max);
            root->right = root->right;
        }
    }
    return root;
}

// 2.用被删除节点的后继节点(是其右子树中的最小值所在的节点)替换被删除的节点
// 返回以root为根节点的二分搜索树中的最小节点
int findMin(struct TreeNode* root) {
    if (!root->left) {
        return root->val;
    }
    return findMin(root->left);
}

struct TreeNode* deleteNode(struct TreeNode* root, int key) {
    if (!root) {
        return NULL;
    }
    if (key < root->val) {
        root->left = deleteNode(root->left, key);
    }
    else if (key > root->val) {
        root->right = deleteNode(root->right, key);
    }
    else {
        if (!root->left) {
            return root->right;
        }
        else if (!root->right) {
            return root->left;
        }
        else {
            // 此时root是被删除的节点
            // min是被删除节点的后继节点的值
            int min = findMin(root->right);
            // 将被删除节点的后继节点放在被删除节点的位置
            root->val = min;
            // 删除原来的后继节点
            root->left = root->left;
            root->right = deleteNode(root->right, min);
        }
    }
    return root;
}