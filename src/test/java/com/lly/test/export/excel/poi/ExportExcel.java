package com.lly.test.export.excel.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 *
 * @author leno
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {

    public void exportExcel(Collection<T> dataset,OutputStream out){
        exportExcel(null,dataset,out);
    }

    public void exportExcel(String[] headers,Collection<T> dataset,
                            OutputStream out){
        exportExcel("测试poi导出文档", headers, dataset, out, "yyy-MM-dd");
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public void exportExcel(String title, String[] headers,
                            Collection<T> dataset, OutputStream out,String pattern){

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(title);
        // 列宽 默认 15字节
        sheet.setDefaultColumnWidth((short) 15);
        // 表格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THICK);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        cellStyle.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style.setFont(font2);

        // 生命一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置。详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        //设置注释的内容
        comment.setString(new HSSFRichTextString("可以在poi中添加注释!"));
        // 设置作者
        comment.setAuthor("dana");
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            HSSFRichTextString textString = new HSSFRichTextString(headers[i]);
            cell.setCellValue(textString);
        }
        // 遍历集合数据，产生数据行
        Iterator<T> iterator = dataset.iterator();
        int index = 0;
        while (iterator.hasNext()){
            index++;
            row = sheet.createRow(index);
            T t = iterator.next();
            // 反射获取字段属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName ="get" + fieldName.substring(0,1).toUpperCase()
                        +fieldName.substring(1);
                try{
                    Class<?> tClass = t.getClass();
                    Method getMethod = tClass.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if(value instanceof Boolean){
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if(!bValue){
                            textValue = "女";
                        }
                    }else if(value instanceof Date){
                        Date date = (Date) value;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        textValue = simpleDateFormat.format(date);
                    }else if(value instanceof byte[]){
                        // 有图片时，设置行高为60px
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px，注意，这里单位的换算
                        sheet.setColumnWidth(i,(short) (35.7 * 80));
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor,workbook.addPicture(bsValue,HSSFWorkbook.PICTURE_TYPE_JPEG));
                    }else{
                        // 其他数据类型都当做字符串简单处理
                        textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if(textValue != null){
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if(matcher.matches()){
                            //是数字就当做double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richTextString = new HSSFRichTextString(textValue);
                            HSSFFont font3 = workbook.createFont();
                            richTextString.applyFont(font3);
                            cell.setCellValue(richTextString);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        try{
            workbook.write(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }





    public static void main(String[] args) {
        // 测试学生
        ExportExcel<Student> ex = new ExportExcel<Student>();
        String[] headers = {"学号", "姓名", "年龄", "性别", "出生日期"};
        List<Student> dataset = new ArrayList<Student>();
        dataset.add(new Student(10000001, "张三", 20, true, new Date()));
        dataset.add(new Student(20000002, "李四", 24, false, new Date()));
        dataset.add(new Student(30000003, "王五", 22, true, new Date()));
        // 测试图书
        ExportExcel<Book> ex2 = new ExportExcel<Book>();
        String[] headers2 = {"图书编号", "图书名称", "图书作者", "图书价格", "图书ISBN",
                "图书出版社", "封面图片"};
        List<Book> dataset2 = new ArrayList<Book>();
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream("book.jpg"));
            byte[] buf = new byte[bis.available()];
            while ((bis.read(buf)) != -1) {
                //
            }
            dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",
                    "清华出版社", buf));
            dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
                    "阳光出版社", buf));
            dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
                    "清华出版社", buf));
            dataset2.add(new Book(4, "c++经典", "leno", 400.33f, "1234567",
                    "清华出版社", buf));
            dataset2.add(new Book(5, "c#入门", "leno", 300.33f, "1234567",
                    "汤春秀出版社", buf));

            OutputStream out = new FileOutputStream("D://a.xls");
            OutputStream out2 = new FileOutputStream("D://b.xls");
            ex.exportExcel(headers, dataset, out);
            ex2.exportExcel(headers2, dataset2, out2);
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
