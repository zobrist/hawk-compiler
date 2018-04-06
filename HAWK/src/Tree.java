
class Tree {
	Tree left, right;
	String key;
	
	Tree(Tree l, String k, Tree r) {
		left = l;
		key = k;
		right = r;
	}
	
	Tree insert(String key, Tree t) {
		if(t == null)
			return new Tree(null, key, null);
		else if(key.compareTo(t.key) < 0) 
			return new Tree(insert(key, t.left), t.key, t.right);
		else if(key.compareTo(t.key) > 0)
			return new Tree(t.left, t.key, insert(key, t.right));
		else
			return new Tree(t.left, key, t.right);
	}
}
