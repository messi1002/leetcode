// 1.递归函数，returnSize、arr是指针，动态修改。
// 递归函数定义: 中序遍历以root节点为根节点的二叉树
void orderTraversal(struct TreeNode* root, int* returnSize, int* arr) {
    // 递归终止条件
    if (!root) {
        return;
    }
    // 递归过程
    // 中序遍历root节点的左子树
    orderTraversal(root->left, returnSize, arr);
    // 处理当前节点
    arr[(*returnSize)++] = root->val;
    // 中序遍历root节点的右子树
    orderTraversal(root->right, returnSize, arr);    
}

// 统计结点数
int makeSize(struct TreeNode* root) {
    if (!root) {
        return 0;
    }
    return makeSize(root->left) + makeSize(root->right) + 1;
}

int* inorderTraversal(struct TreeNode* root, int* returnSize) {
    int size = makeSize(root);
    int* arr = (int*)malloc(sizeof(int) * size);
    *returnSize = 0;
    orderTraversal(root, returnSize, arr);
    return arr;
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

int* inorderTraversal(struct TreeNode* root, int* returnSize) {
    int size = makeSize(root);
    int* arr = (int*)malloc(sizeof(int) * size);
    *returnSize = 0;
    Stack* s = (Stack*)malloc(sizeof(Stack));
    s->array = (struct TreeNode**)malloc(sizeof(struct TreeNode*) * size);
    s->top = -1;
    while (s->top != -1 || root) {
        // 遇到一个节点，就把它压入栈中，并去遍历它的左子树。
        while (root) {
            s->array[++s->top] = root;
            root = root->left;
        }
        // 当左子树遍历结束后，弹出栈顶元素并访问它。
        if (s->top != -1) {
            root = s->array[s->top--];
            arr[(*returnSize)++] = root->val;
            // 再去中序遍历该节点的右子树
            root = root->right;
        }
    }
    return arr;
}