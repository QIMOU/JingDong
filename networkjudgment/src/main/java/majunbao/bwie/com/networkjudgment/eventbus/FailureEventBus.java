package majunbao.bwie.com.networkjudgment.eventbus;

public class FailureEventBus {
    public int failureNum;

    public FailureEventBus(int failureNum) {
        this.failureNum = failureNum;
    }

    public int getFailureNum() {
        return failureNum;
    }

    public void setFailureNum(int failureNum) {
        this.failureNum = failureNum;
    }
}
