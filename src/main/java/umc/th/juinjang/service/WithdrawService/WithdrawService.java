package umc.th.juinjang.service.WithdrawService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.th.juinjang.apiPayload.ExceptionHandler;
import umc.th.juinjang.apiPayload.code.status.ErrorStatus;
import umc.th.juinjang.model.entity.Withdraw;
import umc.th.juinjang.model.entity.enums.WithdrawReason;
import umc.th.juinjang.repository.withdraw.WithdrawRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WithdrawService {

    private final WithdrawRepository withdrawRepository;

    public void addWithdrawReason(List<String> withdrawReasons) {
        for(String reason : withdrawReasons) {
            WithdrawReason withdrawReason = WithdrawReason.valueOf(reason.toUpperCase());

            Withdraw withdraw = withdrawRepository.findByWithdrawReason(withdrawReason)
                    .orElseThrow(() -> new ExceptionHandler(ErrorStatus.WITHDRAW_REASON_NOT_FOUND));

            withdraw.updateCount(withdraw.getCount() + 1);
            withdrawRepository.save(withdraw);
        }
    }
}
