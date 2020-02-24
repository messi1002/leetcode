// 1.递归函数定义: 前序遍历以root节点为根节点的二叉树
private void traversal(TreeNode root, List list) {
    // 递归终止条件
    if (root == null) { 
        return;
    }
    // 递归过程
    // 处理当前节点
    list.add(root.val); 
    // 前序遍历root节点的左子树
    traversal(root.left, list);
    // 前序遍历root节点的右子树
    traversal(root.right, list);
}

public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<Integer>();
    traversal(root, list);
    return list;
}

// 2.迭代 
public List<Integer> preorderTraversal(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    List<Integer> list = new ArrayList<>();
    while (!stack.isEmpty() || root != null) {
        // 遇到一个节点就访问它，然后把它压入栈中，并去遍历它的左子树。
        while (root != null) {
            list.add(root.val);
            stack.push(root);
            root = root.left;
        }
        // 当左子树遍历结束后，弹出栈顶元素。
        if (!stack.isEmpty()) {
            root = stack.pop();
            // 再去先序遍历该节点的右子树
            root = root.right;
        }
    }
    return list;
}