package com.cyryl.kyu3;

import java.util.Arrays;

public class Smaller {
    public static int[] smaller(int[] unsorted) {
        if(unsorted.length == 0) return new int[] {};

        AVLTree avlTree = new AVLTree(unsorted[unsorted.length-1]);
        int[] result = new int[unsorted.length];

        for(int i=unsorted.length-2; i>=0; i--){
            result[i] = avlTree.addAndCount(unsorted[i]);
        }

        System.out.println(Arrays.toString(result));
        return result;
    }

    static public class AVLTree{

        Node root;

        class Node{
            int val;
            int count;
            int height;
            Node left;
            Node right;
            int occurrence;

            public Node(int val){
                this.val = val;
                height = 1;
                count = 1;
                occurrence = 1;
            }
        }

        public AVLTree(int val){
            root = new Node(val);
        }

        public int addAndCount(int val){
            root = insertToAVL(root, val);
            return countSmaller(val);
        }

        public int countSmaller(int val){
            Node node = root;
            int count = 0;

            while (node != null) {
                if (val < node.val) {
                    node = node.left;
                } else if (val > node.val) {
                    count += getCount(node.left) + node.occurrence;
                    node = node.right;
                } else {
                    count += getCount(node.left);
                    return count;
                }
            }
            return count;
        }

        public Node insertToAVL(Node node, int data) {
            if(node == null){
                return new Node(data);
            }
            if(data > node.val){
                node.right = insertToAVL(node.right, data);
            }else if(data < node.val){
                node.left = insertToAVL(node.left, data);
            }else{
                node.occurrence++;
            }

            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
            node.count = updateCount(node);
            //System.out.println("height: " + node.height + " Val: " + node.data);
            int balance = getBalance(node);

            // Left Left Case
            if (balance > 1 && data < node.left.val){
                return rotateRight(node);
            }
            // Left Right Case
            if(balance > 1 && data > node.left.val){
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            // Right Right Case
            if(balance < -1 && data > node.right.val){
                return rotateLeft(node);
            }
            // Right Left Case
            if(balance < -1 && data < node.right.val){
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
            return node;
        }

        private Node rotateRight(Node node){
            Node newRoot = node.left;
            Node transplant = newRoot.right;

            newRoot.right = node;
            node.left = transplant;

            // update height in correct order: node is child of newRoot now
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            node.count = updateCount(node);
            newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;
            newRoot.count = updateCount(newRoot);

            return newRoot;
        }

        private Node rotateLeft(Node node){
            Node newRoot = node.right;
            Node transplant = newRoot.left;

            newRoot.left = node;
            node.right = transplant;

            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
            node.count = updateCount(node);
            newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;
            newRoot.count = updateCount(newRoot);

            return newRoot;
        }

        private int getBalance(Node node){
            if (node == null) return 0;
            return getHeight(node.left) - getHeight(node.right);
        }

        private int getHeight(Node node){
            if (node == null) return 0;
            return node.height;
        }

        private int getCount(Node node){
            if(node == null) return 0;
            return node.count;
        }

        private int updateCount(Node node){
            return node.occurrence + getCount(node.left) + getCount(node.right);
        }
    }
}
