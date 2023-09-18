package com.lunchplay.domain.usecase

import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.repository.MemoRepository
import javax.inject.Inject

class CreateMemo @Inject constructor(
    private val memoRepository: MemoRepository
){
    operator fun invoke(memo: Memo) = memoRepository.createMemo(memo)
}