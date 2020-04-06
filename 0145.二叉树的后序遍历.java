// 1.递归函数定义: 后序遍历以root节点为根节点的二叉树
private void traversal(TreeNode root, List list) {
    // 递归终止条件
    if (root == null) { 
        return;
    }
    // 递归过程
    // 后序遍历root节点的左子树
    traversal(root.left, list);
    // 后序遍历root节点的右子树
    traversal(root.right, list);
    // 处理当前节点
    list.add(root.val); 
}

public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<Integer>();
    traversal(root, list);
    return list;
}

// 2.迭代
// 先将先序遍历的中左右改为中右左，再将遍历结果翻转，即可得到后序遍历的左右中。
public List<Integer> postorderTraversal(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    List<Integer> list = new ArrayList<>();
    while (!stack.isEmpty() || root != null) {
        // 遇到一个节点就访问它，然后把它压入栈中，并去遍历它的右子树。
        while (root != null) {
            list.add(root.val);
            stack.push(root);
            root = root.right;
        }
        // 当右子树遍历结束后，弹出栈顶元素。
        if (!stack.isEmpty()) {
            root = stack.pop();
            // 再去"改写的先序"遍历该节点的左子树
            root = root.left;
        }
    }
    // 将遍历结果翻转
    Collections.reverse(list);
    return list;
}