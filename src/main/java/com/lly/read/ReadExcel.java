package com.lly.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lly.common.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class ReadExcel {

    /**
     * 获取excel中众包aids，进行众包
     * @throws IOException
     * @throws InvalidFormatException
     */
    @Test
    public void testReadExcel() throws IOException, InvalidFormatException {
        String filePath = "C:/Users/Administrator/Desktop/审核/0704";
        ArrayList<Long> brand = new ArrayList<>();
        ArrayList<Long> price = new ArrayList<>();
        ArrayList<Map> msg = new ArrayList<>();

        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File file1 : files) {
                    HashMap<String, Object> message = new HashMap<>();
                    ArrayList<Long> brandTemp = new ArrayList<>();
                    ArrayList<Long> priceTemp = new ArrayList<>();
                    Workbook workbook = WorkbookFactory.create(new FileInputStream(file1));
                    int sheetsNum = workbook.getNumberOfSheets();
                    for (int i = 0; i < sheetsNum; i++) {
                        Sheet sheet = workbook.getSheetAt(i);
                        int lastRowNum = sheet.getLastRowNum();
                        if (sheet.getSheetName().contains("pepsi_unv_all_v")) {
                            Row row = sheet.getRow(0);
                            int index = getIndex(row);
                            if(index == -1){
                                out.println(file1.getName()+"获取品牌提交ID失败!");
                                brandTemp.add(null);
                            }else{
                                for (int k = 1; k <= lastRowNum; k++) {
                                    Row row1 = sheet.getRow(k);
                                    int cellType = row1.getCell(index).getCellType();
                                    if(cellType == 1){
                                        brandTemp.add(Long.parseLong(row1.getCell(index).getStringCellValue()));
                                    }else if(cellType == 0){
//                                        out.println(BigDecimal.valueOf(row1.getCell(index).getNumericCellValue()).longValue());
//                                        String s1 = String.valueOf(row1.getCell(index).getNumericCellValue());
//                                        String s2 = s1.substring(0, s1.indexOf(".")) + s1.substring(s1.indexOf(".")+1);
                                        brandTemp.add(BigDecimal.valueOf(row1.getCell(index).getNumericCellValue()).longValue());
                                    }
                                }
                            }
                        } else if (sheet.getSheetName().contains("pepsi_unv_zdfs_v")) {
                            Row row = sheet.getRow(0);
                            int index = getIndex(row);
                            if(index == -1){
                                out.println(file1.getName()+"获取品牌提交ID失败!");
                                brandTemp.add(null);
                            }else{
                                for (int k = 1; k <= lastRowNum; k++) {
                                    Row row1 = sheet.getRow(k);
                                    priceTemp.add(Long.parseLong(row1.getCell(index).getStringCellValue()));
                                }
                            }
                        }
                        //todo other datasource  to detail
                    }
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("\"brand\"",brandTemp);
                    map.put("\"price\"",priceTemp);
                    message.put("\""+file1.getName()+"\"",map);
                    msg.add(message);
                    brand.addAll(brandTemp);
                    price.addAll(priceTemp);
                    out.println(file1.getName() + ": brand个数：" + brandTemp.size() + " ,price个数: " + priceTemp.size());
                }
        }
        out.println("brand共：" + brand.size() + "，price共：" + price.size());
        File file1 = new File("F:/work/crowd/util/price1.json");
        File file2 = new File("F:/work/crowd/util/brand1.json");
        FileOutputStream fileOutputStream = new FileOutputStream(file1);
        fileOutputStream.write(price.toString().getBytes());

        FileOutputStream fileOutputStream1 = new FileOutputStream(file2);
        fileOutputStream1.write(brand.toString().getBytes());

        FileOutputStream fileOutputStream2 = new FileOutputStream(new File("F:/work/crowd/util/msg.json"));
        fileOutputStream2.write(msg.toString().getBytes());

        fileOutputStream.close();
        fileOutputStream1.close();
        fileOutputStream2.close();
        out.println("读取提交id完成!");

        String brandProjectId = "2914";
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> brandParam = new HashMap<>();
        brandParam.put("id", brandProjectId);
        brandParam.put("aids", brand);

        String brandParamResult = mapper.writeValueAsString(brandParam);
        request(brandParamResult);


        String priceProjectId = "2914_price";
        HashMap<String, Object> priceParam = new HashMap<>();
        priceParam.put("id",priceProjectId);
        priceParam.put("aids",price);
        String priceParamResult = mapper.writeValueAsString(priceParam);
        request(priceParamResult);

        out.println("众包完成");

    }


    /**
     * 删除excel中空白行
     */
    @Test
    public void testDeleteSpaceRow() throws IOException, InvalidFormatException {
        String file = "G:\\config\\exportConfig\\report\\2914 - 副本.xlsx";
        Workbook workbook = WorkbookFactory.create(new FileInputStream(file));
        Sheet sheet1 = workbook.getSheet("sheet1");
        int rowNum = sheet1.getLastRowNum();
        for(int i=0;i<rowNum;i++){
            Row row = sheet1.getRow(i);
            if(row == null || row.cellIterator() == null || !row.cellIterator().hasNext()){
                sheet1.shiftRows(i+1, rowNum, -1);
                rowNum--;
                i--;
            }
        }
        workbook.write(new FileOutputStream("G:\\config\\exportConfig\\report\\2914 - 副本3.xlsx"));
    }

    private int getIndex(Row row) {
        int numberOfCells = row.getPhysicalNumberOfCells();
        for (int j = 0; j < numberOfCells; j++) {
            Cell cell = row.getCell(j);
            String value = cell.getStringCellValue();
            if (StringUtils.equals("提交id", value))
                return j;
        }
        return -1;
    }


    private void request(String param) throws UnsupportedEncodingException {
        String url = "http://120.27.148.138:3000/crowd/byAids";
//        String url  = "http://localhost:3000/crowd/byAids";
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.postRequest(url,param);
    }
}
