
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        // Build the original tree.
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] newParent = parent.clone();

        // Store the closest ancestor for each character.
        int[] last = new int[26];
        Arrays.fill(last, -1);

        // Find new parents using DFS on the original tree.
        findNewParents(0, tree, s, last, newParent);

        // Build the modified tree.
        List<Integer>[] newTree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        // Calculate subtree sizes.
        int[] ans = new int[n];
        calculateSizes(0, newTree, ans);

        return ans;
    }

    private void findNewParents(
        int u,
        List<Integer>[] tree,
        String s,
        int[] last,
        int[] newParent
    ) {
        int c = s.charAt(u) - 'a';

        int previous = last[c];

        if (u != 0 && previous != -1) {
            newParent[u] = previous;
        }

        // Mark current node as the closest ancestor.
        last[c] = u;

        for (int v : tree[u]) {
            findNewParents(v, tree, s, last, newParent);
        }

        // Restore the previous ancestor after DFS.
        last[c] = previous;
    }

    private int calculateSizes(
        int u,
        List<Integer>[] tree,
        int[] ans
    ) {
        int size = 1;

        for (int v : tree[u]) {
            size += calculateSizes(v, tree, ans);
        }

        ans[u] = size;
        return size;
    }
}
