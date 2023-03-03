package com.rs.diuroutine.domain.usecase.main

import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val downloadFile: DownloadFile

)
