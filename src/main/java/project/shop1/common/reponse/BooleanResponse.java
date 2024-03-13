package project.shop1.common.reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BooleanResponse {

    private boolean success;

    public static BooleanResponse of(boolean isSuccess) {
        return BooleanResponse.builder()
                .success(isSuccess)
                .build();
    }
}
