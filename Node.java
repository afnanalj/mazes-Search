/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazedfs;


public class Node {
    int i, j; // call position 

    public Node(int i, int j) {
        this.i = i;
        this.j = j;
}
 public int i()
    {
        return i;
    } 
 public int j() 
    {                      
        return j;
    } 

    public Node left() // go up 
    {
        return new Node(i - 1, j);
    }

    public Node right()// go down
    {
        return new Node(i + 1, j);
    }

    public Node down() // go right 
    {
        return new Node(i, j + 1);
    }

    public Node up()// go up 
    {
        return new Node(i, j - 1);
    }
    

}
