package com.lunchplay.domain

import javax.inject.Inject

class GetMemos @Inject constructor(
    private val memoRepository: MemoRepository
) {
    operator fun invoke() = memoRepository.getMemos()
}