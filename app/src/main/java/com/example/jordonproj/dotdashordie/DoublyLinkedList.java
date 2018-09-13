package com.example.jordonproj.dotdashordie;

import java.util.ArrayList;

/**
 * Created by Jordon Boswell on 06/06/2018.
 */

public class DoublyLinkedList {

    private ArrayList<MorseCharacter> sorted = null;

    private int listLength = 0;
    private Node head;

    public DoublyLinkedList()
    {

    }

    public void addNode(MorseCharacter character)
    {
        Node node = new Node(character);
        Node temp = null;
        int nodeValue = MorseAudio.getMorseLength(character.getMorse());
        if(head == null)
        {
            head = node;
            listLength++;
        }

        else
        {

            temp = head;
            boolean finished = false;
            while(!finished)
            {
                listLength++;
                if(temp.getNodeValue() > nodeValue)
                {
                    node.setRightNode(head); //temp is the head
                    head = node;
                    finished = true;
                }
                else if(temp.rightNode == null)
                {
                    temp.setRightNode(node);
                    node.setLeftNode(temp);
                    finished = true;
                }
                else if(temp.rightNode.getNodeValue() < nodeValue)
                {
                    temp = temp.getRightNode();
                }
                else if(temp.rightNode.nodeValue == nodeValue || temp.rightNode.nodeValue > nodeValue)
                {
                    node.setRightNode(temp.getRightNode());
                    node.setLeftNode(temp);
                    temp.getRightNode().setLeftNode(node);
                    temp.setRightNode(node);
                    finished = true;
                }
            }


        }

    }

    public ArrayList<MorseCharacter> getSorted()
    {
        ArrayList<MorseCharacter> ordered = new ArrayList<MorseCharacter>(listLength);

        if(sorted != null)
        {
            //return hard copy
            for(MorseCharacter c : sorted)
            {
                ordered.add(c);
            }
            return ordered;
        }

        boolean finished = false;
        Node current = head;
        while (!finished)
        {
            ordered.add(current.getHeldCharacter());
            if(current.getRightNode() == null) finished = true;
            else current = current.getRightNode();
        }
        sorted = ordered;
        return ordered;

    }

    private class Node
    {
        private MorseCharacter heldCharacter;
        private Node leftNode;
        private Node rightNode;
        private int nodeValue;

        public Node(MorseCharacter character)
        {
            heldCharacter = character;
            leftNode = null;
            rightNode = null;
            nodeValue = MorseAudio.getMorseLength(heldCharacter.getMorse());
        }

        public MorseCharacter getHeldCharacter() {
            return heldCharacter;
        }

        public void setHeldCharacter(MorseCharacter heldCharacter) {
            this.heldCharacter = heldCharacter;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }

        public int getNodeValue() {
            return nodeValue;
        }

        public void setNodeValue(int nodeValue) {
            this.nodeValue = nodeValue;
        }
    }
}
