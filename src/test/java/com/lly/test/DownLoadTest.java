package com.lly.test;

import com.lly.util.DownLoad;
import org.junit.Test;

public class DownLoadTest {

    @Test
    public void testUrl() throws Exception {
        String url = "http://zhixuetest.oss-cn-hangzhou.aliyuncs.com/hanzhang_test/tfb/word/2018/12/04/南京高二年级英语组卷智提分试卷(B5)_28e7a925-e7d3-46f8-be31-10fb5b55e5db.docx";
        DownLoad.download(url,"/temp","test.docx");
    }

    @Test
    public void testDownLoad() throws Exception {
        String[] urls = {
              "http://images.laba.cn/unilever/21016012_Q8A.png",
                "http://images.laba.cn/unilever/111551_Q8B.png",
                "http://images.laba.cn/unilever/21042800_Q8C.png",

        };

        String[] pcc = {
                "http://images.laba.cn/unilever/111557_Q15A.png",
                "http://images.laba.cn/unilever/21004522_Q15B.jpg",
                "http://images.laba.cn/unilever/111675_Q15F.jpg",
                "http://images.laba.cn/unilever/111702_Q15G.png",
                "http://images.laba.cn/unilever/111642_Q15I.jpg",
                "http://images.laba.cn/unilever/111642_Q15I.jpg",
                "http://images.laba.cn/unilever/111648_Q15K.png"
        };

        String[] oc = {
                "http://images.laba.cn/unilever/20214360_Q20B.png",
                "http://images.laba.cn/unilever/20261334_Q20C.png",
                "http://images.laba.cn/unilever/21183674_Q20E.png"
        };

        String[] hc = {
                "http://images.laba.cn/unilever/21135676_Q3A.png",
                "http://images.laba.cn/unilever/21135691_Q3C.png",
                "http://images.laba.cn/unilever/67036401_Q3E.png",
                "http://images.laba.cn/unilever/67085852_Q3G.png"
        };

        String[] cos_sc = {
                "http://images.laba.cn/unilever/67157990_Q10A.png",
                "http://images.laba.cn/unilever/67157988_Q10B.png",
                ""
        };
        String[] cos_hc={
          "http://images.laba.cn/unilever/67120756_Q5A.png",
                "http://images.laba.cn/unilever/67120744_Q5B.png",
                "http://images.laba.cn/unilever/67120760_Q5C.png",
                "http://images.laba.cn/unilever/67120750_Q5D.png",
                "http://images.laba.cn/unilever/67171205_Q5E.png",
                "http://images.laba.cn/unilever/67171199_Q5F.png",
                "http://images.laba.cn/unilever/67129019_Q5G.png",
                "http://images.laba.cn/unilever/67129048_Q5H.png"
        };

        int i = 1;
        for(String url : urls){
            try{
                DownLoad.download(url,"G:/guess/guess",i+".jpg");
            }catch (Exception e){
                e.printStackTrace();
            }
            i++;
        }
        i = 1;
        for(String url : pcc){
            try{
                DownLoad.download(url,"G:/guess/pcc",i+".jpg");
            }catch (Exception e){
                e.printStackTrace();
            }
            i++;
        }
        i = 1;
        for(String url : oc){
            try{
                DownLoad.download(url,"G:/guess/oc",i+".jpg");
            }catch (Exception e){
                e.printStackTrace();
            }
            i++;
        }
        i = 1;
        for(String url : hc){
            try{
                DownLoad.download(url,"G:/guess/hc",i+".jpg");
            }catch (Exception e){
                e.printStackTrace();
            }
            i++;
        }
        i = 1;
        for(String url : cos_sc){
            try{
                DownLoad.download(url,"G:/guess/cos_sc",i+".jpg");
            }catch (Exception e){
                e.printStackTrace();
            }

            i++;
        }
        i = 1;
        for(String url : cos_hc){
            try{
                DownLoad.download(url,"G:/guess/cos_hc",i+".jpg");
            }catch (Exception e){
                e.printStackTrace();
            }

            i++;
        }

    }


}
