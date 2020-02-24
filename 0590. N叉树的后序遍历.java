// 1.递归函数定义: 后序遍历以root节点为根节点的N叉树
private void traversal(Node root, List list) {
    // 递归终止条件
    if (root == null) {
        return;
    }
    // 递归过程
    // 后序遍历root节点的每个子树
    for (Node node: root.children) {
        traversal(node, list);
    }
    // 处理当前节点
    list.add(root.val);
}
public List<Integer> postorder(Node root) {
    List<Integer> list = new ArrayList<Integer>();
    traversal(root, list);
    return list;
}

// 2.迭代
// 与二叉树的后序遍历——迭代法同理
public List<Integer> postorder(Node root) {
    Stack<Node> stack = new Stack<>();
    List<Integer> list = new ArrayList<>();
    if (root == null) {
        return list;
    }
    stack.push(root);
    while (!stack.isEmpty()) {
        root = stack.pop();
        list.add(root.val);
        // 将N叉树的子树从左向右推入栈中，这样子树出栈的顺序是从右到左。
        for (Node n: root.children) {
            stack.push(n);
        }
    }
    // 将遍历结果翻转
    Collections.reverse(list);
    return list;
}