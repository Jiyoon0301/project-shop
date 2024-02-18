package project.shop1.feature.login.exception;


import lombok.Getter;

@Getter
public class NotExistUserEntity extends RuntimeException{
    private String msg;
    public NotExistUserEntity(String msg){
        this.msg = msg;
    }
}
