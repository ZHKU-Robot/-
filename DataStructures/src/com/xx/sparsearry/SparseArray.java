package com.xx.sparsearry;

import java.io.*;

public class SparseArray {
    public static void main(String[] args){
        //创建一个原始的二维数组11*11
        //0：表示没有棋子，1：表示黑子，2：表示篮子
        int[][] chessArr1=new int[11][11];
        chessArr1[1][2]=1;
        chessArr1[2][3]=2;
        //输出原始的二维数组
        System.out.println("原始的二维数组：");
        for(int[] row:chessArr1){
            for(int data:row){
                System.out.print(data+"\t");
            }
            System.out.println();
        }

        //将二维数组 转 稀疏数组的思想
        //先遍历二维数组，得到非0数据的个数
        int sum=0;
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                if(chessArr1[i][j]!=0)
                    sum++;
            }
        }

        //创建对应的稀疏数组
        int[][] sparseArr=new int[sum+1][3];
        //给稀疏数组赋值
        sparseArr[0][0]=11;
        sparseArr[0][1]=11;
        sparseArr[0][2]=sum;

        //遍历二维数组，将非0的值存放到sparseArr中
        int count=0;//count用于记录是第几个非0数据
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                if(chessArr1[i][j]!=0){
                    count++;
                    sparseArr[count][0]=i;
                    sparseArr[count][1]=j;
                    sparseArr[count][2]=chessArr1[i][j];
                }
            }
        }

        //输出稀疏数组的形式
        System.out.println();
        System.out.println("得到的稀疏数组为~~~");
        for (int i=0;i<sparseArr.length;i++){
            System.out.printf("%d\t%d\t%d\t\n",sparseArr[i][0],sparseArr[i][1],sparseArr[i][2]);
        }
        System.out.println();

        //将数据写入到文件中
        WriteFileStream(sparseArr);

        //将稀疏数组恢复成原始的二维数组
        /**
         * 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的的chessArr2=int[11][11]
         * 在读入稀疏数组后几行的数据，再赋给原始的二维数组即可
         */

        //将文件读出来
        int[][] sparseArr2=ReadFileStream("SparseArray.txt",sum+1,3);


        int[][] chessArr2=new int[sparseArr2[0][0]][sparseArr2[0][1]];
        for(int i=1;i<sparseArr2.length;i++){//sparseArr.length读出的是二维数组sparseArr总共的行数
            chessArr2[sparseArr2[i][0]][sparseArr2[i][1]]=sparseArr2[i][2];
        }

        //输出恢复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组");
        for(int[] row:chessArr2){//这里是将二维数组chessArr2的第一维进行遍历赋到row[]中
            for (int data:row){//这里是将row[]进行遍历然后赋值到data
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
    }

    //写到文件
    public static void WriteFileStream(int[][] o){
        //准备一个文件，内容为空；
        File file=new File("e:\\DataStructPage\\SparseArray.txt");
        OutputStream os=null;
        try{
            if(!file.exists()){
                file.createNewFile();
            }

            //提供FileWriter的对象，用于数据的写出
            //FileWriter fw=new FileWriter(file,false);

            //创建文件写出流
            os=new BufferedOutputStream(new FileOutputStream(file));

            byte[] datas=null;
            String temp=null;
            int count=0;
            for(int[] i:o){
                for(int j:i){
                    //将int类型转化成String类型的三种方法
                    //temp=j+"";
                    //temp=Integer.toString(j);
                    temp=String.valueOf(j);
                    count++;
                    if(count!=o.length*o[0].length){
                       temp=temp+",";
                    }

                    datas=temp.getBytes();//编码
                    os.write(datas,0,datas.length);
                }
            }
            os.flush();

//            //创建基于文件的输出流
//            FileOutputStream fileOutputStream=new FileOutputStream(file);
//            //把数据写入到输出流
//            fileOutputStream.write(data);
//            //关闭输出流
//            fileOutputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读出来

    /**
     * @param FileName 要读取的文件名字
     * @param row   稀疏数组的行
     * @param list  稀疏数组的列
     * @return
     */
    public static int[][] ReadFileStream(String FileName,int row,int list){
        InputStream is=null;
        String str=null;
        int[][] values=new int[row][list];//这里要定义，不然会出现空指针异常
        try {
            File file=new File("e:\\DataStructPage\\"+FileName);
            if(!file.exists()){
                System.out.println("该文件不存在！");
            }
            is=new BufferedInputStream(new FileInputStream(file));
            byte[] flush=new byte[1024*10];//创建容器
            int len=-1;//接收长度
            while((len=is.read(flush))!=-1){
                //字节数组，解码
                str=new String(flush,0,len);
                System.out.println(str);//打印读到的字符串
            }
            String[] stringArr=str.split(",");
            int count=0,head=0,change=0;
            for(int i=0;i< stringArr.length;i++){
                values[head][count]=Integer.parseInt(stringArr[i]);
                count++;
                if(count==3){
                    count=0;
                    head++;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return values;
    }
}
