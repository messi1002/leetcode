// 1.递归函数定义: 中序遍历以root节点为根节点的二叉树
private void traversal(TreeNode root, List list) {
    // 递归终止条件
    if (root == null) { 
        return;
    }
    // 递归过程
    // 中序遍历root节点的左子树
    traversal(root.left, list);
    // 处理当前节点
    list.add(root.val); 
    // 中序遍历root节点的右子树
    traversal(root.right, list);
}

public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<Integer>();
    traversal(root, list);
    return list;
}

// 2.迭代
public List<Integer> inorderTraversal(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    List<Integer> list = new ArrayList<>();
    while (!stack.isEmpty() || root != null) {
        // 遇到一个节点，就把它压入栈中，并去遍历它的左子树。
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        // 当左子树遍历结束后，弹出栈顶元素并访问它。
        if (!stack.isEmpty()) {
            root = stack.pop();
            list.add(root.val);
            // 再去中序遍历该节点的右子树
            root = root.right;
        }
    }
    return list;
}