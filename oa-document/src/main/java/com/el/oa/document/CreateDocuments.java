package com.el.oa.document;

import com.el.oa.common.utils.DateUtils;
import jxl.Workbook;
import jxl.format.*;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.net.URL;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 *
 * @User : Hapic
 * @Date : 2016/9/5 20:14
 */
public class CreateDocuments {


    public static String buildMingmi(String month,String title,String[][] datas){
        File file=null;
        try {
            String path = CreateDocuments.class.getClass().getResource("/").getPath();
            file=new File(path+"/"+title+"-"+month+".xls");

            //创建一个Excel文件
            WritableWorkbook book = Workbook.createWorkbook(file);

            int page=datas.length/28+1;

            for(int a=0;a<page;a++){

                //创建Excel中的页面，设置页面名称，页面号由0开始，页面会按页面号从小到大的顺序在Excel中从左向右排列
                WritableSheet sheet1 = book.createSheet("第"+ DateUtils.da(a+1)+"页", a);

                WritableFont font1=new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD );//宋体不加黑
                WritableCellFormat wcf = new WritableCellFormat(font1);//
                wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//单元格加边框
                wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//上下居中显示
                wcf.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
                Label bigTitle= new Label(0,0,title,wcf);
                sheet1.addCell(bigTitle);
                //设置要合并单元格的下标
                sheet1.mergeCells(0, 0, 5, 0);
                //作用是指定第i+1行的高度，比如将第一行的高度设为200
                for(int i=0;i<31;i++){
                    sheet1.setRowView(i, 500);
                }

//            //作用是指定第i+1列的宽度，比如将第一列的宽度设为30
                String[] titles={"日期","星期","加班人员","饭补","车费","合计"};
                WritableFont font3=new WritableFont(WritableFont.createFont("宋体"),12,WritableFont.NO_BOLD );//宋体不加黑
                WritableCellFormat wcf2 = new WritableCellFormat(font3);//
                wcf2.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//单元格加边框
                wcf2.setBackground(jxl.format.Colour.GRAY_25);//单元格背景色
                for(int i=0;i<6;i++){
                    Label smTile= new Label(i,1,titles[i],wcf2);
                    sheet1.addCell(smTile);
                    sheet1.setColumnView(i, 14);
                }


                WritableCellFormat wcf3 = new WritableCellFormat(font3);//
                wcf3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//单元格加边框
                //{{"6月5日","星期二","张胜","25","0","25"},{"6月5日","星期二","张胜","25","0","25"}};
                int nullDataStart=0;
                int fanbuSum=0;
                int chebuSum=0;
                //插入具体的数据

                int from=a*28;
                int end=(a+1)*28;
                if(end>datas.length){
                    end=datas.length;
                }
                for(int f=from,hj=0;f<end;f++,hj++){
                    nullDataStart++;

                    for(int ik = 0; 6 > ik; ik++){//6列

                        WritableCell cell=null;

                        if(ik==3 || ik==4 || ik==5){
                            int i1 = Integer.parseInt(datas[f][ik]);
                            if(ik==3){
                                fanbuSum+=i1;
                            }else if(ik==4){
                                chebuSum+=i1;
                            }
                            cell = new Number(ik,hj+2,i1,wcf3);
                        }else{
                            cell= new Label(ik,hj+2,datas[f][ik],wcf3);
                        }

                        sheet1.addCell(cell);

                    }

                }

                //打印空出来的记录
                for(int j=nullDataStart;j<28;j++){
                    for(int i=0;i<6;i++){//6列
                        WritableCell  cell= new Label(i,j+2,"",wcf3);
                        sheet1.addCell(cell);
                    }
                }

                String[] hejiDate={"合计"," "," ",fanbuSum+"",chebuSum+"",(fanbuSum+chebuSum)+""};
                for(int i=0;i<6;i++){
                    WritableCell  hejiCel=null;
                    if(i==3 || i==4 || i==5){
                        hejiCel = new Number(i,30,Integer.parseInt(hejiDate[i]),wcf3);
                    }else {
                        hejiCel = new Label(i,30,hejiDate[i],wcf3);
                    }

                    sheet1.addCell(hejiCel);
                }
            }




            //开始执行写入操作
            book.write();
            //关闭流
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public static void main(String[] args) {
        String[][] datas={{"6月5日","星期二","张胜","25","0","25"},{"6月5日","星期二","张胜","25","0","25"}};
//        buildMingmi("08","jjj",datas);
        CreateDocuments.buildMingmi("08","信息技术部门  基础服务组",datas);
    }
}
