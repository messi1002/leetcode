// 明确题意: 求出所有满足 节点值>=L且节点值<=R 的节点的值的和
// 1.递归解法
int rangeSumBST(struct TreeNode* root, int L, int R) {
    if (!root) {
        return 0;
    }
    if (root->val < L) {
        // root.val<L，说明其左子树所有的值都小于L，排除这些节点。
        return rangeSumBST(root->right, L, R); 
    }
    else if (root->val > R) {
        // root.val>R，说明其右子树所有的值都大于R，排除这些节点。
        return rangeSumBST(root->left, L, R);
    }
    else {
        return root->val + rangeSumBST(root->left, L, R) + rangeSumBST(root->right, L, R);
    }
}

// 2.迭代
// 统计结点数
int makeSize(struct TreeNode* root) {
    if (!root) 
        return 0;
    
    return makeSize(root->left) + makeSize(root->right) + 1;
}

typedef struct {
    struct TreeNode** array;
    int top;
} Stack;

int rangeSumBST(struct TreeNode* root, int L, int R) {
    int size = makeSize(root), sum = 0;
    Stack* s = (Stack*)malloc(sizeof(Stack));
    s->array = (struct TreeNode**)malloc(sizeof(struct TreeNode*) * size);
    s->top = -1;
    s->array[++s->top] = root;
    while (s->top != -1) {
        struct TreeNode* node = s->array[s->top--];
        if (node) {
            // root.val<L，说明其左子树所有的值都小于L，排除这些节点。
            if (node->val < L) {
                s->array[++s->top] = node->right;
            }
            // root.val>R，说明其右子树所有的值都大于R，排除这些节点。
            else if (node->val > R) {
                s->array[++s->top] = node->left;
            }
            else {
                sum += node->val;
                s->array[++s->top] = node->left;
                s->array[++s->top] = node->right;
            }
        }
    }
    return sum;
}