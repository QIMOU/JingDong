package majunbao.bwie.com.networkjudgment.eventbus;

public class SuccessEventBus {
    public int successNum;

    public SuccessEventBus(int successNum) {
        this.successNum = successNum;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }
}
