package com.lunchplay.domain.usecase

import com.lunchplay.domain.repository.MemoRepository
import javax.inject.Inject

class GetMemos @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke() = memoRepository.getMemos()
}