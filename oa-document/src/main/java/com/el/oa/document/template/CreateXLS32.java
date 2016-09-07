package com.el.oa.document.template;

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
 * @Date : 2016/7/3 18:29
 */

import jxl.Cell;
import jxl.Workbook;
import jxl.format.*;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateXLS32 {
    public static void main(String[] args) {
        try {
//            测试测试
//            测试测试
            //创建一个Excel文件
            WritableWorkbook book = Workbook.createWorkbook(new File("D:/workSpaces/web-soa/eloa/oa-document/target/bb.xls"));

            //创建Excel中的页面，设置页面名称，页面号由0开始，页面会按页面号从小到大的顺序在Excel中从左向右排列
            WritableSheet sheet1 = book.createSheet("第一页", 0);

            WritableFont font1=new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD );//宋体不加黑
            WritableCellFormat wcf = new WritableCellFormat(font1);//
            wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//单元格加边框
            wcf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//上下居中显示
            wcf.setAlignment(jxl.format.Alignment.CENTRE);//水平居中
            Label bigTitle= new Label(0,0,"信息技术部门  基础服务组",wcf);
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
            wcf2.setBackground(Colour.GRAY_25);//单元格背景色
            for(int i=0;i<6;i++){
                Label smTile= new Label(i,1,titles[i],wcf2);
                sheet1.addCell(smTile);
                sheet1.setColumnView(i, 14);
            }


            WritableCellFormat wcf3 = new WritableCellFormat(font3);//
            wcf3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);//单元格加边框
            String[][] datas={{"6月5日","星期二","张胜","25","0","25"},{"6月5日","星期二","张胜","25","0","25"}};
            int nullDataStart=0;
            int fanbuSum=0;
            int chebuSum=0;
            //插入具体的数据
            for(int j=0;j<datas.length;j++){
                nullDataStart++;
                for(int i=0;i<6;i++){//6列
                    WritableCell cell=null;
                    if(i==3 || i==4 || i==5){
                        int i1 = Integer.parseInt(datas[j][i]);
                        if(i==3){
                            fanbuSum+=i1;
                        }else if(i==4){
                            chebuSum+=i1;
                        }
                         cell = new Number(i,j+2,i1,wcf3);
                    }else{
                        cell= new Label(i,j+2,datas[j][i],wcf3);
                    }
                    sheet1.addCell(cell);
                }

            }
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


//            WritableSheet sheet2 = book.createSheet("Sheet_2", 1);
//
//            //设置单元格的样式
//            WritableCellFormat cellFormat = new WritableCellFormat();
//            //设置水平居中
//            cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
//            //设置垂直居中
//            cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
//            //设置自动换行
//            cellFormat.setWrap(true);
//            //设置显示的字体样式，字体，字号，是否粗体，字体颜色
//            cellFormat.setFont(new WritableFont(WritableFont.createFont("楷体_GB2312"),12,WritableFont.NO_BOLD,false,
//                    UnderlineStyle.NO_UNDERLINE,Colour.RED));
//            //设置单元格背景色
//            cellFormat.setBackground(jxl.format.Colour.BRIGHT_GREEN);
//
//            //创建一个单元格，并按行列坐标进行指定的内容写入 ，最后加入显示的样式
//            Label label = new Label(0, 0, "Excel",cellFormat);
//
//            Label labe2 = new Label(0, 0, "test22222222");
//
//            //插入图片
////            File file=new File("D:\\My Documents\\My Pictures\\test.png");
////            //设置图片位置，前两个参数为插入图片的单元格坐标，后面是设置从插入的单元格开始横向和纵向分别要占用的单元格个数，最后传入图片文件
////            WritableImage image=new WritableImage(6, 0, 3, 3,file);
//
//            //将行列的值写入页面
//            sheet1.addCell(label);
//            //将图片插入页面
////            sheet1.addImage(image);
//            sheet2.addCell(labe2);
//
//            //创建数字类型的行列值
//            Number number1 = new Number(4, 0, 789.123);
//            Number number2 = new Number(4, 0, 789.12345678910);
//
//            //将数字类型的行列值插入指定的页面
//            sheet1.addCell(number1);
//            sheet2.addCell(number2);
//
//            //创建日期类型数据，并添加
//            DateTime dateTime = new DateTime(5, 0, new Date());
//            sheet1.addCell(dateTime);

            //开始执行写入操作
            book.write();
            //关闭流
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
