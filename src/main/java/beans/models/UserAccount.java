package beans.models;

public class UserAccount {
    private long id;
    private Double prepaidMoney;
    private User user;

    public UserAccount() {
    }

    public UserAccount(long id, Double prepaidMoney, User user) {
        this.id = id;
        this.prepaidMoney = prepaidMoney;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPrepaidMoney() {
        return prepaidMoney;
    }

    public void setPrepaidMoney(Double prepaidMoney) {
        this.prepaidMoney = prepaidMoney;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}