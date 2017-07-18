package cn.gojay.model;

/**
 * 计数实体类
 * 生成随机数，统计猜数次数
 */
public class CountModel {
    private int resultNumber;
    private int guessCount;

    /**
     * 无参构造器：生成伪随机数
     */
    public CountModel() {
        resultNumber = (int)(Math.random() * 100);
        guessCount = 0;
    }

    /**
     * 构造器初始化结果数字
     * @param resultNumber
     */
    public CountModel(int resultNumber) {
        this.resultNumber = resultNumber;
        guessCount = 0;
    }

    //Getter，Setter
    public int getGuessCount() {
        return guessCount;
    }

    public void setGuessCount(int guessCount) {
        this.guessCount = guessCount;
    }

    public int getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }
}
