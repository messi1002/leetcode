// 1.递归函数，returnSize、arr是指针，动态修改。
// 递归函数定义: 后序遍历以root节点为根节点的二叉树
void orderTraversal(struct TreeNode* root, int* returnSize, int* arr) {
    // 递归终止条件
    if (!root) {
        return;
    }
    // 递归过程
    // 后序遍历root节点的左子树
    orderTraversal(root->left, returnSize, arr);
    // 后序遍历root节点的右子树
    orderTraversal(root->right, returnSize, arr);    
    // 处理当前节点
    arr[(*returnSize)++] = root->val;
}

// 统计结点数
int makeSize(struct TreeNode* root) {
    if (!root) {
        return 0;
    }
    return makeSize(root->left) + makeSize(root->right) + 1;
}

int* postorderTraversal(struct TreeNode* root, int* returnSize) {
    int size = makeSize(root);
    int* arr = (int*)malloc(sizeof(int) * size);
    *returnSize = 0;
    orderTraversal(root, returnSize, arr);
    return arr;
}

// 2.迭代
// 先将先序遍历的中左右改为中右左，再将遍历结果翻转，即可得到后序遍历的左右中。
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

int* postorderTraversal(struct TreeNode* root, int* returnSize) {
    int size = makeSize(root);
    int* arr = (int*)malloc(sizeof(int) * size);
    Stack* s = (Stack*)malloc(sizeof(Stack));
    s->array = (struct TreeNode**)malloc(sizeof(struct TreeNode*) * size);
    s->top = -1;
    *returnSize = size;
    int row = *returnSize-1;
    while (root || s->top != -1) {
        // 遇到一个节点就访问它，然后把它压入栈中，并去遍历它的右子树。
        while (root) {
            // 反向存储遍历结果
            arr[row--] = root->val;
            s->array[++s->top] = root;
            root = root->right;
        }
        // 当右子树遍历结束后，弹出栈顶元素。
        if (s->top != -1) {
            root = s->array[s->top--];
            // 再去"改写的先序"遍历该节点的左子树
            root = root->left;
        }
    }
    return arr;
}