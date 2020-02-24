// 1.中序遍历二叉搜索树得到升序数列，再找到第k大节点的值
private List<Integer> list;

private void inorderTraversal(TreeNode root) {
    if (root == null) {
        return;
    }
    inorderTraversal(root.left);
    list.add(root.val);
    inorderTraversal(root.right);
}

public int kthLargest(TreeNode root, int k) {
    list = new ArrayList<>();
    inorderTraversal(root);
    return list.get(list.size() - k);
}

// 2.优化: 对于升序数组来说，第k小节点值易求，第k大节点值不易求。
// 所以逆中序遍历二叉搜索树得到降序数列，当遍历到第k个值时，即得第k大节点值，结束递归。
private int k;
private int k_number;

private void inorderTraversal(TreeNode root) {
    if (root == null) {
        return;
    }
    inorderTraversal(root.right);
    if (--k == 0) {
        k_number = root.val;
        return;
    }
    inorderTraversal(root.left);
}

public int kthLargest(TreeNode root, int k) {
    this.k = k;
    inorderTraversal(root);
    return k_number;
}

// 3.迭代(逆中序遍历)
public int kthLargest(TreeNode root, int k) {
    Stack<TreeNode> stack = new Stack<>();
    int count = 0;
    while (!stack.isEmpty() || root != null) {
        // 遇到一个节点，就把它压入栈中，并去遍历它的右子树。
        while (root != null) {
            stack.push(root);
            root = root.right;
        }
        // 当右子树遍历结束后，弹出栈顶元素并访问它。
        if (!stack.isEmpty()) {
            root = stack.pop();
            if (++count == k) {
                return root.val;
            }
            // 再去中序遍历该节点的左子树
            root = root.left;
        }
    }
    return -1;
}