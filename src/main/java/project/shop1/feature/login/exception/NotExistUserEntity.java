package project.shop1.feature.login.exception;


import lombok.Getter;

@Getter
public class NotExistUserEntity extends RuntimeException{
    private String meg;

    public NotExistUserEntity(String meg){
        this.meg = meg;
    }
}
