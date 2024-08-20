package umc.th.juinjang.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import umc.th.juinjang.model.entity.common.BaseEntity;
import umc.th.juinjang.model.entity.enums.WithdrawReason;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Withdraw extends BaseEntity {

    @Id
    @Column(name="withdraw_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long withdrawId;

    @Column(name="reason")
    @Enumerated(EnumType.STRING)
    private WithdrawReason withdrawReason;

    @ColumnDefault("0")
    private Long count;

    public void updateCount(Long count) {
        this.count = count;
    }
}
