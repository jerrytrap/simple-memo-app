package com.lunchplay.domain.usecase

import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.repository.MemoRepository
import javax.inject.Inject

class DeleteMemo @Inject constructor(
    private val memoRepository: MemoRepository
) {
    suspend operator fun invoke(memo: Memo) = memoRepository.deleteMemo(memo)
}