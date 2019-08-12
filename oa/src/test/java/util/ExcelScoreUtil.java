package util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 解析wsp excel的工具类
 */
public class ExcelScoreUtil {
    //定义文件的路径
    public static final String FILEPATH = "E:\\work\\score.xlsx";
    public static final Logger log = LoggerFactory.getLogger(ExcelScoreUtil.class);
    public static final Double MORETHAN = 1.85;
    public static final Double LESSTHAN = 2.89;


    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(FILEPATH);
            Workbook wb;

            if(FILEPATH.toLowerCase().endsWith("xls")) {
                wb = new HSSFWorkbook(fis);
                log.info("找到xls格式的Excel文件！");
                readExcel(wb);
            }else if(FILEPATH.toLowerCase().endsWith("xlsx")) {
                wb = new XSSFWorkbook(fis);
                log.info("找到xlsx格式的Excel文件！");
                readExcel(wb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取excel文件
     * @param workbook
     */
    public static void readExcel(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(5);
        Row hssfRow=sheet.getRow(2);
        //得到表格的行数
        int rowNumber = sheet.getPhysicalNumberOfRows();
        //得到表格的列数
        int columnNum = hssfRow.getPhysicalNumberOfCells();
        log.info("表格的行数:"+rowNumber+"，表格的列数："+columnNum);
        /**
         * winScoreMap中 第一个参数为最低赔率，第二个参数为打出的赔率
         */
        Map<Double,Double> winScoreMap = new HashMap<Double, Double>();
        //下面的map分别对应胜和平
        Map<Double,Double> wonMap = new HashMap<Double, Double>();
        Map<Double,Double> drawMap = new HashMap<Double, Double>();
        Map<Double,Double> loseMap = new HashMap<Double, Double>();

        //计算凯利的map
        Map<Double,List<Double>> kailiMap = new HashMap<Double, List<Double>>();


        for(int i=0;i<rowNumber;i++){
            Row row = sheet.getRow(i);
            Double win = 0.00;
            Double drawWin = 0.00;
            Double loseWin = 0.00;
            List<Double> listTemp = new ArrayList<Double>();
            for (int j = 6; j < columnNum -3 ; j++) {
                Cell cell = row.getCell(j);
                if(null == getCellValue(cell)){
                    continue;
                }
                if(isColorFonte(cell)){
                    win = Double.parseDouble(getCellValue(cell));
                }

                if(isColorFonte(cell) && j == 7){
                    drawWin = Double.parseDouble(getCellValue(cell));
                }

                if(isColorFonte(cell) && win > LESSTHAN && j != 7){
                    loseWin = Double.parseDouble(getCellValue(cell));
                }

                listTemp.add(Double.parseDouble(getCellValue(cell)));

            }

            if(null == kailiMap.get(win)){
                kailiMap.put(win,listTemp);
            }else{
                kailiMap.put(win+Math.random()*0.01,listTemp);
            }

            kailiMap.put(win,listTemp);


            Collections.sort(listTemp);
            //把平配存入到drawMap中
            if (listTemp.size() != 0){

                if(null == drawMap.get(listTemp.get(0))){
                    drawMap.put(listTemp.get(0),drawWin);
                }else {
                    drawMap.put(listTemp.get(0)+Math.random()*0.01,drawWin);
                }


            }

            if(listTemp.size() != 0){
                if(null == winScoreMap.get(listTemp.get(0))){
                    winScoreMap.put(listTemp.get(0),win);
                    //把最低配存入到winMap中
                    wonMap.put(listTemp.get(0),win);
                }else{
                    winScoreMap.put(listTemp.get(0)+Math.random()*0.01,win);
                    wonMap.put(listTemp.get(0)+Math.random()*0.01,win);
                }


            }

            if(listTemp.size() != 0){
                if(null == loseMap.get(listTemp.get(0))){
                    loseMap.put(listTemp.get(0),loseWin);
                }else{
                    loseMap.put(listTemp.get(0)+Math.random()*0.01,loseWin);
                }

            }



        }

        /*for (Map.Entry<Double, Double> entry : drawMap.entrySet()){
            log.info("最低配："+entry.getKey()+",打出赔率："+entry.getValue());
        }

        log.warn("===================");
        for (Map.Entry<Double, Double> entry : selectData(winScoreMap,MOVETHAN,LESSTHAN).entrySet()){
            log.info("最低配："+entry.getKey()+",打出赔率："+entry.getValue());
        }*/

        AnalysisProfitModel(selectData(winScoreMap,MORETHAN,LESSTHAN));
        log.info("胜出概率："+computWinProbability(selectData(wonMap,MORETHAN,LESSTHAN))+"%");
        log.info("打平概率："+computDrawOrLoseProbability(selectData(drawMap,MORETHAN,LESSTHAN))+"%");
        log.info("输球概率："+computDrawOrLoseProbability(selectData(loseMap,MORETHAN,LESSTHAN))+"%");

        log.info("胜的投注建议："+recommendOption(computWinProbability(selectData(wonMap,MORETHAN,LESSTHAN))));
        log.info("平的投注建议："+recommendOption(computDrawOrLoseProbability(selectData(drawMap,MORETHAN,LESSTHAN))));
        log.info("负的投注建议："+recommendOption(computDrawOrLoseProbability(selectData(loseMap,MORETHAN,LESSTHAN))));
    }

    /**
     * 推荐投注选项
     * @param probability 当前赔率的概率
     * @return
     */
    public static String recommendOption(int probability){
        double probabity,times,odds, a0 , a1;
        String result;
        odds = (MORETHAN + LESSTHAN) / 2;
        times = odds - 1;
        probabity = Double.valueOf(probability) /100;
        a0 = probabity * times - (1-probabity);
        if(a0 > 0){
            a1 = a0 / probability;
            result = "建议投注本金的："+a1+"倍";
        }else{
            result = "不建议投注,因为此赔率的回报率为："+formatValue(a0);
        }


        return result;
    }

    /**
     * 计算胜负平各自的概率
     * @param selectSourceMap
     */
    public static int computWinProbability(Map<Double,Double> selectSourceMap){
        int count = 0;
        int percent;
        for (Map.Entry<Double,Double> map : selectSourceMap.entrySet()){
            String odds = formatValue(map.getKey());
            if(Double.parseDouble(odds) == map.getValue()){
                count++;
            }
        }
        percent = formatPercnet(count , selectSourceMap.size());

        return  percent;
    }

    public static int computDrawOrLoseProbability(Map<Double,Double> selectSourceMap){
        int count = 0;
        int percent;
        for (Map.Entry<Double,Double> map : selectSourceMap.entrySet()){
            if(0.0 != map.getValue()){
                count++;
            }
        }
        percent = formatPercnet(count , selectSourceMap.size());

        return  percent;
    }

    /**
     * 筛选所需要的数据
     * @param dataSourceMap
     * @param movethan 所选赔率大于此值
     * @param lessthan 所选赔率小于此值
     */
    public static Map<Double,Double> selectData(Map<Double,Double> dataSourceMap,Double movethan, Double lessthan){
        Map<Double,Double> selectMap = new HashMap<Double, Double>();
        for (Map.Entry<Double, Double> entry : dataSourceMap.entrySet()){
            if(movethan <= entry.getKey() && entry.getKey() <= lessthan){
                selectMap.put(entry.getKey(),entry.getValue());
            }
        }

        return selectMap;
    }

    /**
     * 分析盈利模式
     * @param selectSourceMap
     * @return
     */
    public static Double AnalysisProfitModel(Map<Double,Double> selectSourceMap){
        int payMoney = 200;
        Double earnMoney = 0.0;
        for (Map.Entry<Double, Double> map : selectSourceMap.entrySet()) {
            //log.info("最低配:"+map.getKey());
            String low = formatValue(map.getKey());
            //如果最低配是打出赔率，表示买中红单
            log.info("购买的赔率:"+low+",打出的赔率："+map.getValue());
            if(Double.parseDouble(low) == map.getValue()){
                earnMoney += payMoney * (map.getValue()-1);
                //log.info("赚了："+payMoney * (map.getValue()-1));
            }else{
                earnMoney -= payMoney;
                //log.info("亏了后："+earnMoney);
            }

            //log.info("当前结余:"+earnMoney);
        }

        log.info("最终战绩："+earnMoney);
        log.info("********************************************************");
        log.info("共计："+selectSourceMap.size()+"手");
        return earnMoney;

    }


    /**
     * 判断单元格的字体是否是有颜色（有颜色表明是打出的结果）
     * @param cell
     * @return
     */
    public static boolean isColorFonte(Cell cell){
        boolean flag = false;
        XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
        XSSFFont font = cellStyle.getFont();
        XSSFColor xssfColor = font.getXSSFColor();
        if("FFFF5E01".equals(xssfColor.getARGBHex())){
            flag = true;
        }

        return flag;
    }

    /**
     * 返回单元格的值（只返回数字值，其他的返回空）
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String cellValue = null;
        if(cell.getCellType() == CellType.NUMERIC) {
            cellValue = decimalFormat.format(cell.getNumericCellValue());
        }

        return cellValue;
    }

    /**
     * 格式化double 保留两位小数
     * @param value
     * @return
     */
    public static String formatValue(Double value){
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(value);
    }

    /**
     * a/b的值转成百分比
     * @param a
     * @param b
     * @return
     */
    public static int formatPercnet(int a ,int b){
        int percent = 0;
        if(a != 0){
            percent = (int)((new BigDecimal((float) a / b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);
        }

        return percent;
    }


}
