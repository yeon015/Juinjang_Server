package umc.th.juinjang.repository.withdraw;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.th.juinjang.model.entity.Withdraw;
import umc.th.juinjang.model.entity.enums.WithdrawReason;

import java.util.Optional;

public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {
    Optional<Withdraw> findByWithdrawReason(WithdrawReason withdrawReason);
}
