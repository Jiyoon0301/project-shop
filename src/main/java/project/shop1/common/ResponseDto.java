package project.shop1.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String msg;
    private Object data;

    public ResponseDto(String msg, Object data){
        this.msg=msg;
        this.data=data;
    }
    public ResponseDto(String msg){
        this.msg=msg;
    }
}
