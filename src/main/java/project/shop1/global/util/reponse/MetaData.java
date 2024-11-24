package project.shop1.global.util.reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetaData {

    private int totalCount;

    public MetaData(int totalCount){
        this.totalCount = totalCount;
    }
}