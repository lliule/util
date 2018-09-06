package com.lly.test.export.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.ElementList;
import com.lly.pdf.MyXMLWorkerFontProvider;
import com.lly.pdf.Position;
import com.lly.pdf.QuestionTitle;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.Arrays;

public class PDFReaderTest {

    private String comment = "<span style='font-family:SimHei;'>填涂须知：</span><span style='font-size:8px;'><br/>1、您的反馈会为您下次推送的个性化" +
            "学习方案更标准，更有针对性！<br/>\r\n2、按照题号在对应的答题区域内涂写，超出各题区域的答案无效，在草稿纸上，试卷上作答无效。" +
            "<br/>3、客观题根据答案只区分会答（Y）、不会答（N）2种情况，主观题根据掌握情况区分分为初步了解（1）、基本理解（2）、全面了解（3）3种情况" +
            "<br/>4、答题纸不得折叠、污染、穿孔、撕破等。</span>";
    private String answerTemplate = "";
    private String title = "2017-2018第二学期高二（理科）期末考试";
    private String subTitle = "个性化学习手册自反馈题卡";
    private String pageSize = "A4";

    private String section1Title = "一、客观题（参考答案认真填写）";
    private String[] sectionOneQuestionTitles = {"1","1.1","2","2.1","3","3.1","4","4.1","5","5.1","6","6.1"};
    private int section1Row = 5;

    @Test
    public void testReader() throws Exception {
        Document document = new Document();
        PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));
        document.open();

        setTitle(document);
        setHr(document);
        setComment(document,instance);
        setSection1(document);
//        setSection2(document);

        document.close();
        instance.close();

    }

    private void setSection1(Document document) throws Exception {
        PdfDiv pdfDiv = new PdfDiv();
        // 最外层的 table ,只有一个 cell
        PdfPTable outPTable = new PdfPTable(1);

        PdfPCell outCell = new PdfPCell();
        outCell.setPadding(0f);

        PdfDiv div = new PdfDiv();

        PdfPTable inTable = new PdfPTable(new float[]{200});
        inTable.setWidthPercentage(100f);


        PdfPCell titleCell = new PdfPCell();
        titleCell.setBorderWidth(0);
        // 设置标题
        titleCell.addElement(new Paragraph(section1Title,getPdfChineseFont()));
        inTable.addCell(titleCell);
        // 添加题序信息
        PdfPCell answerList = new PdfPCell();
        answerList.setBorderWidth(0f);
        answerList.setPadding(0f);

        // 计算表格
        int section1Col = (sectionOneQuestionTitles.length % section1Row == 0) ? (sectionOneQuestionTitles.length / section1Row) : (sectionOneQuestionTitles.length/section1Row+1);
        float[] widths = new float[section1Col];
        for (int i = 0; i < widths.length; i++) {
            // todo 根据尺寸动态计算每列的宽度
            widths[i] = 100;
        }
        PdfPTable table = new PdfPTable(widths);
        String yes = "[Y]",no = "[N]";
        QuestionTitle[] titles = convert();
        for (int i = 0; i < section1Row; i++) {
            int index = 0;
            for (QuestionTitle questionTitle : titles) {
                if(questionTitle.getPosition().getY() == i){
                    PdfPCell cell = new PdfPCell();
                    cell.setBorderWidth(0);
                    cell.addElement(new Paragraph(questionTitle.getValue()+" " + yes + " " + no,getPdfChineseFont()));
                    table.addCell(cell);
                    index ++ ;
                }
            }
            while (index < section1Col){
                PdfPCell cell = new PdfPCell();
                cell.setBorderWidth(0);
                table.addCell(cell);
                index ++ ;
            }
        }



        answerList.addElement(table);
        inTable.addCell(answerList);
        div.addElement(inTable);
        outCell.addElement(div);
        outPTable.addCell(outCell);
        pdfDiv.addElement(outPTable);
        document.add(pdfDiv);

    }

    private void setHr(Document document) throws DocumentException {
        PdfDiv pdfDiv = new PdfDiv();
        pdfDiv.setHeight(10f);
        document.add(pdfDiv);
    }

    private void setComment(Document document,PdfWriter writer) throws Exception {
        PdfDiv commentDiv = new PdfDiv();
        float[] widths = {300,100};
        PdfPTable table = new PdfPTable(widths);
        PdfPCell cellOne = new PdfPCell();
//        ElementList elements = XMLWorkerHelper.parseToElementList(comment, null);
        ElementList elements = MyXMLWorkerFontProvider.parseToElementList(comment, null);
        for (Element element : elements) {
            cellOne.addElement(element);
        }

        table.addCell(cellOne);
        PdfPCell cell2 = new PdfPCell();
        cell2.addElement(Image.getInstance("C:\\Users\\Admin\\Desktop\\cat.jpg"));
        table.addCell(cell2);
        commentDiv.addElement(table);
        document.add(commentDiv);
    }

    private void setTitle(Document document) throws Exception {
        // 标题
        PdfDiv titleDiv = new PdfDiv();
        Paragraph paragraph = new Paragraph(title, getPdfChineseFont());
        paragraph.setAlignment(Element.ALIGN_CENTER);
        // 副标题
        Paragraph subTitle = new Paragraph(this.subTitle, getPdfChineseFont());
        subTitle.setAlignment(Element.ALIGN_CENTER);
        titleDiv.addElement(paragraph);
        titleDiv.addElement(subTitle);
        document.add(titleDiv);

    }


    // 单例
    public Font getPdfChineseFont() throws Exception {
        BaseFont chineseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(chineseFont, 12, Font.NORMAL);
        return font;
    }


    @Test
    public void testConver(){
        QuestionTitle[] convert = convert();
        System.out.println(Arrays.toString(convert));
    }

    private QuestionTitle[] convert(){
        QuestionTitle[] questionTitles = new QuestionTitle[sectionOneQuestionTitles.length];
        int index = 0;
        for (int i = 0; i < sectionOneQuestionTitles.length; i+=section1Row) {
            for (int j = 0; j < section1Row; j++) {
                if(sectionOneQuestionTitles.length > (i + j)){
                    Position position = new Position(index, j);
                    QuestionTitle questionTitle = new QuestionTitle(sectionOneQuestionTitles[i+j]);
                    questionTitle.setPosition(position);
                    questionTitles[i+j] = questionTitle;
                }
            }
            index ++;
        }
        return questionTitles;
    }




}
