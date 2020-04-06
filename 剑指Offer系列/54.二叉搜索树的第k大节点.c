// 1.中序遍历二叉搜索树得到升序数列，再找到第k大节点的值。
void orderTraversal(struct TreeNode* root, int* returnSize, int* arr) {
    if (!root) {
        return;
    }
    orderTraversal(root->left, returnSize, arr);
    arr[(*returnSize)++] = root->val;
    orderTraversal(root->right, returnSize, arr);    
}

// 统计结点数
int makeSize(struct TreeNode* root) {
    if (!root) {
        return 0;
    }
    return makeSize(root->left) + makeSize(root->right) + 1;
}

int kthLargest(struct TreeNode* root, int k){
    int size = makeSize(root);
    int* arr = (int*)malloc(sizeof(int) * size);
    int* returnSize = (int*)malloc(sizeof(int));
    *returnSize = 0;
    orderTraversal(root, returnSize, arr);
    return arr[size - k];
}

// 2.优化: 对于升序数组来说，第k小节点值易求，第k大节点值不易求。
// 所以逆中序遍历二叉搜索树得到降序数列，当遍历到第k个值时，即得第k大节点值，结束递归。
void orderTraversal(struct TreeNode* root, int* returnSize, int* arr, int k, int* k_number) {
    if (!root) {
        return;
    }
    orderTraversal(root->right, returnSize, arr, k, k_number);    
    if (++(*returnSize) == k) {
        *k_number = root->val;
    }
    orderTraversal(root->left, returnSize, arr, k, k_number);
}

// 统计结点数
int makeSize(struct TreeNode* root) {
    if (!root) {
        return 0;
    }
    return makeSize(root->left) + makeSize(root->right) + 1;
}

int kthLargest(struct TreeNode* root, int k){
    int size = makeSize(root);
    int* arr = (int*)malloc(sizeof(int) * size);
    int* returnSize = (int*)malloc(sizeof(int));
    int* k_number = (int*)malloc(sizeof(int));
    *returnSize = 0;
    *k_number = 0;
    orderTraversal(root, returnSize, arr, k, k_number);
    return *k_number;
}

// 3.迭代(逆中序遍历)
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

int kthLargest(struct TreeNode* root, int k){
    int size = makeSize(root);
    int* arr = (int*)malloc(sizeof(int) * size);
    Stack* s = (Stack*)malloc(sizeof(Stack));
    s->array = (struct TreeNode**)malloc(sizeof(struct TreeNode*) * size);
    s->top = -1;
    int count = 0;
    while (s->top != -1 || root) {
        // 遇到一个节点，就把它压入栈中，并去遍历它的右子树。
        while (root) {
            s->array[++s->top] = root;
            root = root->right;
        }
        // 当右子树遍历结束后，弹出栈顶元素并访问它。
        if (s->top != -1) {
            root = s->array[s->top--];
            if (++count == k) {
                return root->val;
            }
            // 再去中序遍历该节点的左子树
            root = root->left;
        }
    }
    return -1;
}