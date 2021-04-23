import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;
    BinaryTree parent;

    public BinaryTree(int new_value) {
        value = new_value;
        left = null;
        right = null;
        parent = null;
    }

    public void insert(int new_value) {
        if (new_value < value) {
            if (left == null) {
                left = new BinaryTree(new_value);
                left.parent = this;
            }
            else {
                left.insert(new_value);
            }
        }
        if (new_value > value) {
            if (right == null) {
                right = new BinaryTree(new_value);
                right.parent = this;
            }
            else {
                right.insert(new_value);
            }
        }
    }

    public int getLength() {
        int left_length, right_length;

        if (left == null) {
            left_length = 0;
        }
        else {
            left_length = left.getLength();
        }

        if (right == null) {
            right_length = 0;
        }
        else {
            right_length = right.getLength();
        }

        if (left_length > right_length) {
            return left_length + 1;
        }
        else {
            return right_length + 1;
        }
    }

    public boolean isBalanced() {
        int left_length, right_length;

        if (left == null) {
            left_length = 0;
        }
        else {
            left_length = left.getLength();
        }

        if (right == null) {
            right_length = 0;
        }
        else {
            right_length = right.getLength();
        }

        return (Math.abs(right_length - left_length) <= 1);
    }

    public BinaryTree balanceLeftSmall() {
        BinaryTree a = this;
        BinaryTree b = a.left;

        a.left = b.right;
        b.right = a;

        if (parent != null) {
            if (parent.left == this) {
                parent.left = b;
                b.parent = parent;
            }
            else {
                parent.right = b;
                b.parent = parent;
            }
        }

        a.parent = b;

        return b;
    }

    public BinaryTree balancedLeftBig() {
        BinaryTree a = this;
        BinaryTree b = a.left;
        BinaryTree c = a.left.right;

        BinaryTree m = null, n = null;
        if (c.left != null) {
            m = c.left;
        }
        if (c.right != null) {
            n = c.right;
        }

        b.right = m;
        a.left = n;
        c.left = b;
        c.right = a;

        if (parent != null) {
            if (parent.left == this) {
                parent.left = c;
                c.parent = parent;
            }
            else {
                parent.right = c;
                c.parent = parent;
            }
        }

        a.parent = c;
        b.parent = c;

        return c;
    }

    public BinaryTree balanceLeft() {
        int left_length = 0, right_length = 0;

        if (left.left == null) {
            left_length = 0;
        }
        else {
            left_length = left.left.getLength();
        }
        if (left.right == null) {
            right_length = 0;
        }
        else {
            right_length = left.right.getLength();
        }

        if (right_length <= left_length) {
            return balanceLeftSmall();
        }
        else {
            return balancedLeftBig();
        }
    }

    public BinaryTree balanceRightSmall() {
        BinaryTree a = this;
        BinaryTree b = a.right;

        a.right = b.left;
        b.left = a;

        if (parent != null) {
            if (parent.left == this) {
                parent.left = b;
                b.parent = parent;
            }
            else {
                parent.right = b;
                b.parent = parent;
            }
        }

        a.parent = b;

        return b;
    }

    public BinaryTree balancedRightBig() {
        BinaryTree a = this;
        BinaryTree b = a.right;
        BinaryTree c = a.right.left;

        BinaryTree m = null, n = null;
        if (c.left != null) {
            m = c.left;
        }
        if (c.right != null) {
            n = c.right;
        }

        a.right = m;
        b.left = n;
        c.left = a;
        c.right = b;

        if (parent != null) {
            if (parent.left == this) {
                parent.left = c;
                c.parent = parent;
            }
            else {
                parent.right = c;
                c.parent = parent;
            }
        }

        a.parent = c;
        b.parent = c;

        return c;
    }

    public BinaryTree balanceRight() {
        int left_length = 0, right_length = 0;

        if (right.left == null) {
            left_length = 0;
        }
        else {
            left_length = right.left.getLength();
        }
        if (right.right == null) {
            right_length = 0;
        }
        else {
            right_length = right.right.getLength();
        }

        if (left_length <= right_length) {
            return balanceRightSmall();
        }
        else {
            return balancedRightBig();
        }
    }

    public BinaryTree balance(int left_length, int right_length) {
        if (left_length > right_length) {
            return balanceLeft();
        }
        else {
            return balanceRight();
        }
    }

    public BinaryTree insertAndBalace(int new_value) {
        int left_length = 0, right_length = 0;

        if (new_value < value) {
            if (left == null) {
                left = new BinaryTree(new_value);
                left.parent = this;
                left_length = 1;
            }
            else {
                left.insertAndBalace(new_value);
                left_length = left.getLength();
            }

            if (right == null) {
                right_length = 0;
            }
            else {
                right_length = right.getLength();
            }
        }
        if (new_value > value) {
            if (right == null) {
                right = new BinaryTree(new_value);
                right.parent = this;
                right_length = 1;
            }
            else {
                right.insertAndBalace(new_value);
                right_length = right.getLength();
            }

            if (left == null) {
                left_length = 0;
            }
            else {
                left_length = left.getLength();
            }
        }

        if (Math.abs(right_length - left_length) > 1) {
            return balance(left_length, right_length);
        }
        else {
            return this;
        }
    }

    public void print(int length) {
        if (right != null) {
            right.print(length + 1);
        }

        for (int i = 0; i < length; i++) {
            System.out.print("\t");
        }
        System.out.println(value);

        if (left != null) {
            left.print(length + 1);
        }
    }
}
