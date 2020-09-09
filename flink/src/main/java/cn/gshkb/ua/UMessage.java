package cn.gshkb.ua;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UMessage {

    private String uid;

    private String createTime;
}
