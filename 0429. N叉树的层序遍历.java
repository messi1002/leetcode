// 1.迭代
public List<List<Integer>> levelOrder(Node root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) {
        return res;
    }
    Queue<Node> queue = new LinkedList<>();
    // 将根节点入队
    queue.add(root);
    while (!queue.isEmpty()) {
        // 使用count分层
        int count = queue.size();
        List<Integer> list = new ArrayList<>();
        while (count-- > 0) {
            // 节点出队
            Node node = queue.poll();
            // 访问该节点
            list.add(node.val);
            // 其所有孩子从左到右依次入队
            for (Node n: node.children) {
                queue.add(n);
            }
        }
        res.add(list);
    }
    return res;
}