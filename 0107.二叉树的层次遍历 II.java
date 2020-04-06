// 1.改写二叉树的层次遍历I的代码
// 迭代
public List<List<Integer>> levelOrderBottom(TreeNode root)   {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
        return res;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    // 将根节点入队
    queue.add(root);
    while (!queue.isEmpty()) {
        // 使用count分层
        int count = queue.size();
        List<Integer> list = new ArrayList<>();
        while (count-- > 0) {
            // 节点出队
            TreeNode node = queue.poll();
            // 访问该节点
            list.add(node.val);
            // 其左右孩子入队
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        res.add(list);
    }
    // 最后翻转res即可
    Collections.reverse(res);
    return res;
}

// 2.使用LinkedList
// 迭代
public List<List<Integer>> levelOrderBottom(TreeNode root)   {
    LinkedList<List<Integer>> res = new LinkedList<>();
    if (root == null) {
        return res;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    // 将根节点入队
    queue.add(root);
    while (!queue.isEmpty()) {
        // 使用count分层
        int count = queue.size();
        List<Integer> list = new ArrayList<>();
        while (count-- > 0) {
            // 节点出队
            TreeNode node = queue.poll();
            // 访问该节点
            list.add(node.val);
            // 其左右孩子入队
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        // 每次都往队头塞
        res.addFirst(list);
    }
    return res;
}