package com.xx.queue;

import java.util.Scanner;

public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);
        System.out.println("——————————请输入相应的操作指令——————————");
        Scanner scanner=new Scanner(System.in);
        boolean loop=true;
        char key=' ';
        while(loop){
            System.out.println("查看全部数据请输”s(show)“");
            System.out.println("添加数据请输“a(add)”");
            System.out.println("取出数据请输“g(get)”");
            System.out.println("查看头数据请输“h(head)”");
            System.out.println("退出请输“e(exit)”");
            key=scanner.next().charAt(0);//接收一个字符
            switch(key){
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个数字：");
                    int value=scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try{
                        int res= queue.getQueue();
                        System.out.println("取出的数据是："+res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());//如果getQueue()抛出异常，则在这里获取抛出的异常信息并打印出来！
                    }
                    break;
                case 'h':
                    try{
                        int res=queue.headQueue();
                        System.out.println("队列的头数据是："+res);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop=false;
                    break;
                default:
                    System.out.println("输入的指令有误，请重新输入！");
                    break;
            }
        }
        System.out.println("程序退出...");
    }
}
class ArrayQueue{
    private int MaxSize;//队列的最大存储量
    private int front;//指向队列头数据的前一个位置
    private int rear;//指向队列尾的数据
    private int[] arr;//队列的容器
    public ArrayQueue(int MaxSize){
        this.MaxSize=MaxSize;
        this.front=-1;
        this.rear=-1;
        arr=new int[MaxSize];
    }
    public boolean isFull(){
        return rear==MaxSize-1;
    }
    public boolean isEmpty(){
        return front==rear;
    }
    public void addQueue(int n){
        if(isFull()){//判断队列是否为满！
            System.out.println(("队列已满，不能继续存入数据..."));
            return;
        }
        rear++;//rear后移一位
        arr[rear]=n;
    }
    public int getQueue(){
        if(isEmpty()){//判断是否为空
            //抛出异常
            throw new RuntimeException("队列为空，无数据可取...");
        }
        front++;//front后移一位
        return arr[front];
    }
    //显示队列的所有数据
    public void showQueue(){
        if(isEmpty()){
            System.out.println("队列为空，无数据展示  ...");
            return;
        }
        for(int i=0;i<arr.length;i++){
            System.out.printf("arr[%d]=%d\t",i,arr[i]);
        }
    }
    //显示队列的头数据，注意不是取出数据
    public int headQueue(){//方法为int类型的，所以不能使用 return； 需要使用throw...
        if (isEmpty()){
            throw new RuntimeException("队列为空，无数据展示  ...");
        }
        return arr[front+1];
    }
}