// 1.递归函数定义: 前序遍历以root节点为根节点的N叉树
private void traversal(Node root, List list) {
    // 递归终止条件
    if (root == null) {
        return;
    }
    // 递归过程
    // 处理当前节点
    list.add(root.val);
    // 前序遍历root节点的每个子树
    for (Node node: root.children) {
        traversal(node, list);
    }
}
public List<Integer> preorder(Node root) {
    List<Integer> list = new ArrayList<Integer>();
    traversal(root, list);
    return list;
}

// 2.迭代
public List<Integer> preorder(Node root) {
    Stack<Node> stack = new Stack<>();
    List<Integer> list = new ArrayList<>();
    if (root == null) {
        return list;
    }
    stack.push(root);
    while (!stack.isEmpty()) {
        root = stack.pop();
        list.add(root.val);
        // 将N叉树的子树从右向左推入栈中，这样子树出栈的顺序是从左到右。
        Collections.reverse(root.children);
        for (Node n: root.children) {
            stack.push(n);
        }
    }
    return list;
}